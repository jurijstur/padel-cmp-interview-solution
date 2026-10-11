package io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments

import io.bifri.interview.cmp.padel.feature.tournament.domain.location.TournamentWithDistance
import io.bifri.interview.cmp.padel.feature.tournament.domain.model.TournamentsTournament
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments.TournamentsNavigationAction.ShowError
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments.TournamentsPartialState.DataLoaded
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments.TournamentsPartialState.DataLoading
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments.TournamentsPartialState.DataLoadingError
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue

class TournamentsStateReducerTest {
    @Test
    fun unavailableLocationHidesDistancesAndKeepsOrder() {
        val state = reduceTournamentsState(
            prevState = TournamentsState(),
            changes = DataLoaded(
                items = listOf(
                    TournamentWithDistance(tournament = tournament(id = 2L, location = "Barcelona"), distanceKm = null),
                    TournamentWithDistance(tournament = tournament(id = 1L, location = "London"), distanceKm = null),
                ),
                isLocationUnavailable = true,
            ),
        )

        assertTrue(state.uiState.isLocationUnavailable)
        assertFalse(state.uiState.isDataLoading)
        assertEquals(
            expected = listOf("Barcelona", "London"),
            actual = state.uiState.items.map { it.tournament.location },
        )
        assertTrue(state.uiState.items.all { it.distanceKm == null })
    }

    @Test
    fun loadingKeepsTheCurrentList() {
        val loaded = reduceTournamentsState(
            prevState = TournamentsState(),
            changes = DataLoaded(
                items = listOf(
                    TournamentWithDistance(tournament = tournament(id = 1L, location = "London"), distanceKm = null),
                ),
                isLocationUnavailable = false,
            ),
        )
        val refreshing = reduceTournamentsState(
            prevState = loaded,
            changes = DataLoading,
        )

        assertTrue(refreshing.uiState.isDataLoading)
        assertFalse(refreshing.uiState.isLocationUnavailable)
        assertEquals(loaded.uiState.items, refreshing.uiState.items)
    }

    @Test
    fun loadErrorKeepsTheListAndReportsTheFailure() {
        val loaded = reduceTournamentsState(
            prevState = TournamentsState(),
            changes = DataLoaded(
                items = listOf(
                    TournamentWithDistance(tournament = tournament(id = 1L, location = "London"), distanceKm = null),
                ),
                isLocationUnavailable = false,
            ),
        )
        val error = IllegalStateException("offline")
        val failed = reduceTournamentsState(
            prevState = loaded,
            changes = DataLoadingError(error),
        )

        assertFalse(failed.uiState.isDataLoading)
        assertEquals(loaded.uiState.items, failed.uiState.items)
        assertNull(failed.uiState.items.first().distanceKm)
        assertEquals(error, assertIs<ShowError>(failed.event?.peekContent()).error)
    }

    private fun tournament(id: Long, location: String) = TournamentsTournament(
        id = id,
        name = location,
        location = location,
        country = "GB",
        level = null,
        status = null,
        startDate = null,
        endDate = null,
    )
}
