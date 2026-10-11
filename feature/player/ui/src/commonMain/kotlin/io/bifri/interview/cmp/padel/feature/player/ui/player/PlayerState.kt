package io.bifri.interview.cmp.padel.feature.player.ui.player

import io.bifri.interview.cmp.padel.core.ui.Event
import io.bifri.interview.cmp.padel.feature.player.domain.model.PlayerDetails

internal data class PlayerState(
    val uiState: UiState = UiState(),
    val event: Event<PlayerNavigationAction>? = null,
) {
    data class UiState(
        val player: PlayerDetails? = null,
        val isDataLoading: Boolean = false,
        val isDataLoadError: Boolean = false,
        val isPlayerNotFound: Boolean = false,
    )
}
