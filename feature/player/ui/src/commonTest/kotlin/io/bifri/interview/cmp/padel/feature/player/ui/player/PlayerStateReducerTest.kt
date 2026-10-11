package io.bifri.interview.cmp.padel.feature.player.ui.player

import io.bifri.interview.cmp.padel.feature.player.domain.exception.PlayerNotFoundException
import io.bifri.interview.cmp.padel.feature.player.domain.model.PlayerDetails
import io.bifri.interview.cmp.padel.feature.player.ui.player.PlayerNavigationAction.ShowError
import io.bifri.interview.cmp.padel.feature.player.ui.player.PlayerPartialState.DataLoaded
import io.bifri.interview.cmp.padel.feature.player.ui.player.PlayerPartialState.DataLoading
import io.bifri.interview.cmp.padel.feature.player.ui.player.PlayerPartialState.DataLoadingError
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PlayerStateReducerTest {

    @Test
    fun loadErrorKeepsAScrollableFailureWhileRefreshing() {
        val error = IllegalStateException("offline")
        val failed = reducePlayerState(
            prevState = PlayerState(),
            changes = DataLoadingError(error),
        )

        assertTrue(failed.uiState.isDataLoadError)
        assertFalse(failed.uiState.isPlayerNotFound)
        assertFalse(failed.uiState.isDataLoading)
        assertEquals(error, assertIs<ShowError>(failed.event?.peekContent()).error)

        val refreshing = reducePlayerState(
            prevState = failed,
            changes = DataLoading,
        )

        assertTrue(refreshing.uiState.isDataLoadError)
        assertTrue(refreshing.uiState.isDataLoading)
    }

    @Test
    fun missingPlayerIsDistinctFromALoadError() {
        val missing = reducePlayerState(
            prevState = PlayerState(),
            changes = DataLoadingError(PlayerNotFoundException(playerId = 99L)),
        )

        assertTrue(missing.uiState.isPlayerNotFound)
        assertFalse(missing.uiState.isDataLoadError)
        assertNull(missing.uiState.player)
        assertNull(missing.event)
    }

    @Test
    fun successfulLoadClearsNotFoundAndTheLoadError() {
        val failed = reducePlayerState(
            prevState = PlayerState(),
            changes = DataLoadingError(PlayerNotFoundException(playerId = 1L)),
        )
        val loaded = reducePlayerState(
            prevState = failed,
            changes = DataLoaded(previewPlayer()),
        )

        assertEquals("Arturo Coello", loaded.uiState.player?.name)
        assertFalse(loaded.uiState.isPlayerNotFound)
        assertFalse(loaded.uiState.isDataLoadError)
        assertFalse(loaded.uiState.isDataLoading)
    }

    @Test
    fun refreshFailureKeepsTheLoadedPlayer() {
        val loaded = reducePlayerState(
            prevState = PlayerState(),
            changes = DataLoaded(previewPlayer()),
        )
        val error = IllegalStateException("offline")
        val failed = reducePlayerState(
            prevState = loaded,
            changes = DataLoadingError(error),
        )

        assertEquals("Arturo Coello", failed.uiState.player?.name)
        assertTrue(failed.uiState.isDataLoadError)
        assertEquals(error, assertIs<ShowError>(failed.event?.peekContent()).error)
    }

    private fun previewPlayer() = PlayerDetails(
        id = 1L,
        name = "Arturo Coello",
        photoUrl = null,
        category = "men",
        nationality = "ES",
        ranking = "1",
        points = 14280,
        elo = 2140,
        height = 190,
        side = "drive",
        hand = "right",
        birthplace = "Madrid",
        age = "24",
    )
}
