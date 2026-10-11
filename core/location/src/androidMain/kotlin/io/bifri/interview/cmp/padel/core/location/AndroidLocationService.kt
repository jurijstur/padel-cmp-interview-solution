package io.bifri.interview.cmp.padel.core.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Looper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

internal class AndroidLocationService(
    private val context: Context,
) : LocationService {
    override fun observe(): Flow<DeviceLocation> = callbackFlow {
        val manager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        val listener = LocationListener { location ->
            trySend(location.toDeviceLocation())
        }
        when {
            manager == null -> trySend(DeviceLocation.Unavailable)
            !context.hasLocationPermission() -> trySend(DeviceLocation.Denied)
            else -> manager.deliverUpdates(listener) { reading -> trySend(reading) }
        }
        awaitClose { manager?.removeUpdates(listener) }
    }.changedAtMostOncePerMinute().flowOn(Dispatchers.Main)
}

private fun Context.hasLocationPermission(): Boolean {
    val fineGranted = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
        PackageManager.PERMISSION_GRANTED
    val coarseGranted = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) ==
        PackageManager.PERMISSION_GRANTED
    return fineGranted || coarseGranted
}

@SuppressLint("MissingPermission")
private fun LocationManager.deliverUpdates(
    listener: LocationListener,
    emit: (DeviceLocation) -> Unit,
) {
    val provider = when {
        isProviderEnabled(LocationManager.GPS_PROVIDER) -> LocationManager.GPS_PROVIDER
        isProviderEnabled(LocationManager.NETWORK_PROVIDER) -> LocationManager.NETWORK_PROVIDER
        else -> null
    }
    if (provider == null) {
        emit(DeviceLocation.Unavailable)
        return
    }
    try {
        getLastKnownLocation(provider)?.let { location -> emit(location.toDeviceLocation()) }
        requestLocationUpdates(
            provider,
            MinimumLocationIntervalMillis,
            MinimumDistanceMeters,
            listener,
            Looper.getMainLooper(),
        )
    } catch (_: SecurityException) {
        emit(DeviceLocation.Denied)
    } catch (_: IllegalArgumentException) {
        emit(DeviceLocation.Unavailable)
    }
}

private fun Location.toDeviceLocation(): DeviceLocation = deviceLocation(
    latitude = latitude,
    longitude = longitude,
)

private const val MinimumDistanceMeters = 0f
