package io.bifri.interview.cmp.padel.feature.player.ui.player

import io.bifri.interview.cmp.padel.feature.player.domain.model.PlayerDetails

internal sealed interface PlayerPartialState {
    data object DataLoading : PlayerPartialState
    data class DataLoaded(val player: PlayerDetails) : PlayerPartialState
    data class DataLoadingError(val error: Throwable) : PlayerPartialState
    data class Navigate(val navigationAction: PlayerNavigationAction) : PlayerPartialState
}
