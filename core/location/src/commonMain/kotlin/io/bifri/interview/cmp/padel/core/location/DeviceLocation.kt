package io.bifri.interview.cmp.padel.core.location

import kotlin.math.round

sealed interface DeviceLocation {
    data object Pending : DeviceLocation

    data object Denied : DeviceLocation

    data object Unavailable : DeviceLocation

    data class Known(
        val latitude: Double,
        val longitude: Double,
    ) : DeviceLocation
}

internal fun deviceLocation(latitude: Double, longitude: Double): DeviceLocation.Known = DeviceLocation.Known(
    latitude = latitude.toRoundedCoordinate(),
    longitude = longitude.toRoundedCoordinate(),
)

private fun Double.toRoundedCoordinate(): Double = round(this * CoordinateScale) / CoordinateScale

private const val CoordinateScale = 10_000.0
