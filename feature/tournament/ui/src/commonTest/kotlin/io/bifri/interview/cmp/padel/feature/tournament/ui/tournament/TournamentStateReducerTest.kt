package io.bifri.interview.cmp.padel.feature.tournament.ui.tournament

import io.bifri.interview.cmp.padel.feature.tournament.domain.model.Tournament
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournament.TournamentNavigationAction.ShowError
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournament.TournamentPartialState.DataLoaded
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournament.TournamentPartialState.DataLoading
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournament.TournamentPartialState.DataLoadingError
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class TournamentStateReducerTest {

    @Test
    fun loadErrorKeepsAScrollableFailureWhileRefreshing() {
        val error = IllegalStateException("offline")
        val failed = reduceTournamentState(
            prevState = TournamentState(),
            changes = DataLoadingError(error),
        )

        assertTrue(failed.uiState.isDataLoadError)
        assertFalse(failed.uiState.isDataLoading)
        assertEquals(error, assertIs<ShowError>(failed.event?.peekContent()).error)

        val refreshing = reduceTournamentState(
            prevState = failed,
            changes = DataLoading,
        )

        assertTrue(refreshing.uiState.isDataLoadError)
        assertTrue(refreshing.uiState.isDataLoading)
    }

    @Test
    fun successfulLoadClearsTheError() {
        val failed = reduceTournamentState(
            prevState = TournamentState(),
            changes = DataLoadingError(IllegalStateException("offline")),
        )
        val loaded = reduceTournamentState(
            prevState = failed,
            changes = DataLoaded(tournament()),
        )

        assertFalse(loaded.uiState.isDataLoadError)
        assertFalse(loaded.uiState.isDataLoading)
        assertEquals(tournament(), loaded.uiState.tournament)
    }

    @Test
    fun refreshFailureKeepsTheLoadedTournament() {
        val loaded = reduceTournamentState(
            prevState = TournamentState(),
            changes = DataLoaded(tournament()),
        )
        val failed = reduceTournamentState(
            prevState = loaded,
            changes = DataLoadingError(IllegalStateException("offline")),
        )

        assertEquals(tournament(), failed.uiState.tournament)
        assertTrue(failed.uiState.isDataLoadError)
        assertFalse(failed.uiState.isDataLoading)
    }

    private fun tournament() = Tournament(
        id = 1L,
        name = "Rome",
        location = null,
        country = null,
        timezone = null,
        level = null,
        status = null,
        venueName = null,
        venueAddress = null,
        prizeAmount = null,
        prizeCurrency = null,
        photoUrl = null,
        courtType = null,
        startDate = null,
        endDate = null,
    )
}
