package io.bifri.interview.cmp.padel.feature.player.ui.players

import io.bifri.interview.cmp.padel.feature.player.domain.model.Player
import io.bifri.interview.cmp.padel.feature.player.ui.players.PlayersNavigationAction.ShowError
import io.bifri.interview.cmp.padel.feature.player.ui.players.PlayersPartialState.DataLoaded
import io.bifri.interview.cmp.padel.feature.player.ui.players.PlayersPartialState.DataLoading
import io.bifri.interview.cmp.padel.feature.player.ui.players.PlayersPartialState.DataLoadingError
import io.bifri.interview.cmp.padel.feature.player.ui.players.PlayersPartialState.NameQueryChanged
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class PlayersStateReducerTest {

    @Test
    fun loadErrorKeepsAScrollableFailureWhileRefreshing() {
        val error = IllegalStateException("offline")
        val failed = reducePlayersState(
            prevState = PlayersState(),
            changes = DataLoadingError(error),
        )

        assertTrue(failed.uiState.isDataLoadError)
        assertFalse(failed.uiState.isDataLoading)
        assertEquals(error, assertIs<ShowError>(failed.event?.peekContent()).error)

        val refreshing = reducePlayersState(
            prevState = failed,
            changes = DataLoading,
        )

        assertTrue(refreshing.uiState.isDataLoadError)
        assertTrue(refreshing.uiState.isDataLoading)
    }

    @Test
    fun successfulLoadClearsTheErrorAndKeepsTheQuery() {
        val searching = reducePlayersState(
            prevState = PlayersState(),
            changes = NameQueryChanged("galan"),
        )
        val loaded = reducePlayersState(
            prevState = searching,
            changes = DataLoaded(listOf(player("Alejandro Galan"), player("Arturo Coello"))),
        )

        assertFalse(loaded.uiState.isDataLoadError)
        assertEquals("galan", loaded.uiState.nameQuery)
        assertEquals(2, loaded.uiState.players.size)
        assertEquals(listOf("Alejandro Galan"), loaded.uiState.visiblePlayers.map { it.name })
    }

    @Test
    fun refreshFailureKeepsTheLoadedPlayers() {
        val loaded = reducePlayersState(
            prevState = PlayersState(),
            changes = DataLoaded(listOf(player("Arturo Coello"))),
        )
        val failed = reducePlayersState(
            prevState = loaded,
            changes = DataLoadingError(IllegalStateException("offline")),
        )

        assertEquals("Arturo Coello", failed.uiState.players.single().name)
        assertTrue(failed.uiState.isDataLoadError)
    }

    @Test
    fun nameQueryFiltersAsTheUserTypesAndClearRestoresTheList() {
        val players = listOf(player("Alejandro Galan"), player("Alejandro Arroyo"), player("Arturo Coello"))
        val loaded = reducePlayersState(
            prevState = PlayersState(),
            changes = DataLoaded(players),
        )
        val galan = reducePlayersState(
            prevState = loaded,
            changes = NameQueryChanged("gal"),
        )
        val alejandro = reducePlayersState(
            prevState = loaded,
            changes = NameQueryChanged("alejandro"),
        )
        val cleared = reducePlayersState(
            prevState = galan,
            changes = NameQueryChanged("  "),
        )

        assertEquals(listOf("Alejandro Galan"), galan.uiState.visiblePlayers.map { it.name })
        assertEquals(
            listOf("Alejandro Galan", "Alejandro Arroyo"),
            alejandro.uiState.visiblePlayers.map { it.name },
        )
        assertEquals(players.map { it.name }, cleared.uiState.visiblePlayers.map { it.name })
        assertEquals(players, cleared.uiState.players)
    }

    private fun player(name: String) = Player(
        id = name.hashCode().toLong(),
        name = name,
        shortName = null,
        photoUrl = null,
        nationality = "ES",
        ranking = "1",
        points = null,
        category = "men",
    )
}
