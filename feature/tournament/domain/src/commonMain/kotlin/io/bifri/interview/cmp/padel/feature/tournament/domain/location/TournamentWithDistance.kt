package io.bifri.interview.cmp.padel.feature.tournament.domain.location

import io.bifri.interview.cmp.padel.feature.tournament.domain.model.TournamentsTournament
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt

data class TournamentWithDistance(
    val tournament: TournamentsTournament,
    val distanceKm: Int?,
)

fun sortTournamentsByDistance(
    tournaments: List<TournamentsTournament>,
    latitude: Double?,
    longitude: Double?,
): List<TournamentWithDistance> {
    if (latitude == null || longitude == null) {
        return tournaments.toUndistanced()
    }
    val located = tournaments.map { tournament ->
        tournament.withDistance(latitude = latitude, longitude = longitude)
    }
    val nearestFirst = located.filter { it.distanceKm != null }.sortedBy { it.distanceKm }
    val missingCoordinates = located.filter { it.distanceKm == null }
    return nearestFirst + missingCoordinates
}

private fun List<TournamentsTournament>.toUndistanced(): List<TournamentWithDistance> = map { tournament ->
    TournamentWithDistance(tournament = tournament, distanceKm = null)
}

private fun TournamentsTournament.withDistance(
    latitude: Double,
    longitude: Double,
): TournamentWithDistance {
    val distanceKm = cityCoordinate(location = location, country = country)
        ?.distanceKm(latitude = latitude, longitude = longitude)
    return TournamentWithDistance(tournament = this, distanceKm = distanceKm)
}

private fun CityCoordinate.distanceKm(latitude: Double, longitude: Double): Int {
    val startLatitude = latitude.toRadians()
    val endLatitude = this.latitude.toRadians()
    val latitudeDelta = (this.latitude - latitude).toRadians()
    val longitudeDelta = (this.longitude - longitude).toRadians()
    val halfLatitude = sin(latitudeDelta / HalfTurn)
    val halfLongitude = sin(longitudeDelta / HalfTurn)
    val halfChord = halfLatitude * halfLatitude +
        cos(startLatitude) * cos(endLatitude) * halfLongitude * halfLongitude
    val complement = 1.0 - halfChord
    val angularDistance = HalfTurn * atan2(sqrt(halfChord), sqrt(complement))
    return (EarthRadiusKm * angularDistance).roundToInt()
}

private fun Double.toRadians(): Double = this * PI / DegreesPerHalfTurn

private const val EarthRadiusKm = 6371.0
private const val DegreesPerHalfTurn = 180.0
private const val HalfTurn = 2.0
