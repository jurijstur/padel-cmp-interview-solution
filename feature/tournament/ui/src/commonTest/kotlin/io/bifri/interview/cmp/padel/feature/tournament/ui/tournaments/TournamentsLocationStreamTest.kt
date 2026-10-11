package io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments

import io.bifri.interview.cmp.padel.core.location.DeviceLocation
import io.bifri.interview.cmp.padel.core.location.LocationService
import io.bifri.interview.cmp.padel.feature.tournament.domain.model.TournamentsTournament
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments.TournamentsPartialState.DataLoaded
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments.TournamentsPartialState.DataLoading
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class TournamentsLocationStreamTest {
    @Test
    fun alreadyGrantedPermissionStillShowsTheListBeforeAFix() = runTest {
        val locations = locationReadings(
            permission = flowOf(true),
            service = silentLocationService(),
        )
        val loaded = sortedTournamentLoads(
            loads = flowOf(TournamentLoad.Ready(listOf(london(), bali()))),
            locations = locations,
        ).toList().filterIsInstance<DataLoaded>()

        assertTrue(loaded.isNotEmpty())
        assertEquals(listOf("London", "Bali"), loaded.first().items.map { it.tournament.location })
        assertTrue(loaded.first().items.all { it.distanceKm == null })
        assertFalse(loaded.first().isLocationUnavailable)
    }

    @Test
    fun aNewLocationResortsTheLoadedList() = runTest {
        val loaded = sortedTournamentLoads(
            loads = flowOf(TournamentLoad.Ready(listOf(bali(), london()))),
            locations = flowOf(DeviceLocation.Known(LondonLatitude, LondonLongitude)),
        ).toList().filterIsInstance<DataLoaded>().single()

        assertEquals(listOf("London", "Bali"), loaded.items.map { it.tournament.location })
        assertEquals(expected = 0, actual = loaded.items.first().distanceKm)
        val baliDistance = loaded.items.last().distanceKm
        assertTrue(baliDistance != null && baliDistance > loaded.items.first().distanceKm!!)
    }

    @Test
    fun deniedPermissionKeepsOrderWithoutDistances() = runTest {
        val loaded = sortedTournamentLoads(
            loads = flowOf(TournamentLoad.Ready(listOf(bali(), london()))),
            locations = flowOf(DeviceLocation.Denied),
        ).toList().filterIsInstance<DataLoaded>().single()

        assertTrue(loaded.isLocationUnavailable)
        assertEquals(listOf("Bali", "London"), loaded.items.map { it.tournament.location })
        assertTrue(loaded.items.all { it.distanceKm == null })
    }

    @Test
    fun refreshKeepsDistancesFromTheCurrentLocation() = runTest {
        val tournaments = listOf(bali(), london())
        val partials = sortedTournamentLoads(
            loads = flow {
                emit(TournamentLoad.Ready(tournaments))
                emit(TournamentLoad.Loading)
                emit(TournamentLoad.Ready(tournaments))
            },
            locations = flowOf(DeviceLocation.Known(LondonLatitude, LondonLongitude)),
        ).toList()
        val loaded = partials.filterIsInstance<DataLoaded>()

        assertIs<DataLoading>(partials.first { it is DataLoading })
        assertEquals(expected = 2, actual = loaded.size)
        assertEquals(loaded.first(), loaded.last())
    }

    private fun silentLocationService() = object : LocationService {
        override fun observe(): Flow<DeviceLocation> = emptyFlow()
    }

    private fun london() = tournament(id = 1L, location = "London", country = "GB")

    private fun bali() = tournament(id = 2L, location = "Bali", country = "ID")

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
