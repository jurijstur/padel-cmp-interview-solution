package io.bifri.interview.cmp.padel.feature.match.ui.match

import io.bifri.interview.cmp.padel.feature.match.domain.model.Match
import io.bifri.interview.cmp.padel.feature.match.ui.match.MatchNavigationAction.ShowError
import io.bifri.interview.cmp.padel.feature.match.ui.match.MatchPartialState.DataLoaded
import io.bifri.interview.cmp.padel.feature.match.ui.match.MatchPartialState.DataLoading
import io.bifri.interview.cmp.padel.feature.match.ui.match.MatchPartialState.DataLoadingError
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class MatchStateReducerTest {

    @Test
    fun loadErrorKeepsAScrollableFailureWhileRefreshing() {
        val error = IllegalStateException("offline")
        val failed = reduceMatchState(
            prevState = MatchState(),
            changes = DataLoadingError(error),
            tournamentName = TournamentName,
            totalCourts = TotalCourts,
        )

        assertTrue(failed.uiState.isDataLoadError)
        assertFalse(failed.uiState.isDataLoading)
        assertEquals(error, assertIs<ShowError>(failed.event?.peekContent()).error)

        val refreshing = reduceMatchState(
            prevState = failed,
            changes = DataLoading,
            tournamentName = TournamentName,
            totalCourts = TotalCourts,
        )

        assertTrue(refreshing.uiState.isDataLoadError)
        assertTrue(refreshing.uiState.isDataLoading)
    }

    @Test
    fun successfulLoadClearsTheError() {
        val failed = reduceMatchState(
            prevState = MatchState(),
            changes = DataLoadingError(IllegalStateException("offline")),
            tournamentName = TournamentName,
            totalCourts = TotalCourts,
        )
        val loaded = reduceMatchState(
            prevState = failed,
            changes = DataLoaded(match()),
            tournamentName = TournamentName,
            totalCourts = TotalCourts,
        )

        assertFalse(loaded.uiState.isDataLoadError)
        assertFalse(loaded.uiState.isDataLoading)
        assertEquals(match(), loaded.uiState.match)
        assertEquals(TournamentName, loaded.uiState.tournamentName)
        assertEquals(TotalCourts, loaded.uiState.totalCourts)
    }

    @Test
    fun refreshFailureKeepsTheLoadedMatch() {
        val loaded = reduceMatchState(
            prevState = MatchState(),
            changes = DataLoaded(match()),
            tournamentName = TournamentName,
            totalCourts = TotalCourts,
        )
        val failed = reduceMatchState(
            prevState = loaded,
            changes = DataLoadingError(IllegalStateException("offline")),
            tournamentName = TournamentName,
            totalCourts = TotalCourts,
        )

        assertEquals(match(), failed.uiState.match)
        assertTrue(failed.uiState.isDataLoadError)
        assertFalse(failed.uiState.isDataLoading)
    }

    private fun match() = Match(
        id = 1L,
        courtName = null,
        roundName = null,
        teamOne = null,
        teamTwo = null,
        scores = emptyList(),
        winnerTeam = null,
    )

    private companion object {
        const val TournamentName = "Madrid P1"
        const val TotalCourts = 8
    }
}
