package io.bifri.interview.cmp.padel.feature.player.ui.players

import io.bifri.interview.cmp.padel.core.ui.Event
import io.bifri.interview.cmp.padel.feature.player.domain.model.Player

internal data class PlayersState(
    val uiState: UiState = UiState(),
    val event: Event<PlayersNavigationAction>? = null,
) {
    data class UiState(
        val players: List<Player> = emptyList(),
        val visiblePlayers: List<Player> = emptyList(),
        val nameQuery: String = "",
        val isDataLoading: Boolean = false,
        val isDataLoadError: Boolean = false,
    )
}
