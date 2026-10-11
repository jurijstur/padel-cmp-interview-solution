package io.bifri.interview.cmp.padel.core.location

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusDenied
import platform.CoreLocation.kCLAuthorizationStatusRestricted
import platform.CoreLocation.kCLLocationAccuracyHundredMeters
import platform.Foundation.NSError
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
internal class IosLocationService : LocationService {
    override fun observe(): Flow<DeviceLocation> = callbackFlow {
        val manager = CLLocationManager()
        val delegate = LocationDelegate(
            onLocation = { latitude, longitude -> trySend(deviceLocation(latitude, longitude)) },
            onStatus = { status -> manager.handleAuthorization(status) { reading -> trySend(reading) } },
            onUnavailable = { trySend(DeviceLocation.Unavailable) },
        )
        manager.delegate = delegate
        manager.desiredAccuracy = kCLLocationAccuracyHundredMeters
        manager.distanceFilter = MinimumMovementMeters
        manager.handleAuthorization(manager.authorization()) { reading -> trySend(reading) }
        awaitClose {
            manager.stopUpdatingLocation()
            manager.delegate = null
            delegate.retainUntilClosed()
        }
    }.changedAtMostOncePerMinute().flowOn(Dispatchers.Main)
}

@OptIn(ExperimentalForeignApi::class)
private fun CLLocationManager.handleAuthorization(
    status: LocationAuthorization,
    emit: (DeviceLocation) -> Unit,
) {
    when (status) {
        LocationAuthorization.Authorized -> startUpdatingLocation()
        LocationAuthorization.Denied -> emit(DeviceLocation.Denied)
        LocationAuthorization.NotDetermined -> requestWhenInUseAuthorization()
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun CLLocationManager.authorization(): LocationAuthorization = when (authorizationStatus) {
    kCLAuthorizationStatusAuthorizedAlways,
    kCLAuthorizationStatusAuthorizedWhenInUse,
    -> LocationAuthorization.Authorized

    kCLAuthorizationStatusDenied,
    kCLAuthorizationStatusRestricted,
    -> LocationAuthorization.Denied

    else -> LocationAuthorization.NotDetermined
}

private const val MinimumMovementMeters = 100.0

private enum class LocationAuthorization {
    Authorized,
    Denied,
    NotDetermined,
}

@OptIn(ExperimentalForeignApi::class)
private class LocationDelegate(
    private val onLocation: (Double, Double) -> Unit,
    private val onStatus: (LocationAuthorization) -> Unit,
    private val onUnavailable: () -> Unit,
) : NSObject(), CLLocationManagerDelegateProtocol {
    override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
        val latest = didUpdateLocations.lastOrNull() as? CLLocation ?: manager.location ?: return
        val coordinate = latest.coordinate
        onLocation(
            coordinate.useContents { latitude },
            coordinate.useContents { longitude },
        )
    }

    override fun locationManagerDidChangeAuthorization(manager: CLLocationManager) {
        onStatus(manager.authorization())
    }

    @Suppress("UnusedParameter")
    override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
        when (manager.authorization()) {
            LocationAuthorization.Denied -> onStatus(LocationAuthorization.Denied)
            else -> onUnavailable()
        }
    }

    fun retainUntilClosed(): Int = hashCode()
}
