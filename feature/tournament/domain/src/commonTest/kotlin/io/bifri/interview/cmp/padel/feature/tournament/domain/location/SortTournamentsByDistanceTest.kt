package io.bifri.interview.cmp.padel.feature.tournament.domain.location

import io.bifri.interview.cmp.padel.feature.tournament.domain.model.TournamentsTournament
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class SortTournamentsByDistanceTest {
    @Test
    fun nearestCityComesFirstAndUnknownCitiesStayLast() {
        val sorted = sortTournamentsByDistance(
            tournaments = listOf(barcelona(), unknown(), london()),
            latitude = LondonLatitude,
            longitude = LondonLongitude,
        )

        assertEquals(
            expected = listOf("London", "Barcelona", "Unknown"),
            actual = sorted.map { it.tournament.location },
        )
        val londonDistance = sorted.first { it.tournament.location == "London" }.distanceKm
        val barcelonaDistance = sorted.first { it.tournament.location == "Barcelona" }.distanceKm
        assertEquals(expected = 0, actual = londonDistance)
        assertTrue(barcelonaDistance != null && londonDistance != null && barcelonaDistance > londonDistance)
        assertNull(sorted.last().distanceKm)
    }

    @Test
    fun missingDeviceLocationKeepsTheOriginalOrderWithoutDistances() {
        val sorted = sortTournamentsByDistance(
            tournaments = listOf(barcelona(), london()),
            latitude = null,
            longitude = null,
        )

        assertEquals(
            expected = listOf("Barcelona", "London"),
            actual = sorted.map { it.tournament.location },
        )
        assertTrue(sorted.all { it.distanceKm == null })
    }

    private fun london() = tournament(id = 1L, location = "London", country = "GB")

    private fun barcelona() = tournament(id = 2L, location = "Barcelona", country = "ES")

    private fun unknown() = tournament(id = 3L, location = "Unknown", country = "XX")

    private fun tournament(
        id: Long,
        location: String,
        country: String,
    ) = TournamentsTournament(
        id = id,
        name = location,
        location = location,
        country = country,
        level = null,
        status = null,
        startDate = null,
        endDate = null,
    )

    private companion object {
        const val LondonLatitude = 51.5074
        const val LondonLongitude = -0.1278
    }
}
