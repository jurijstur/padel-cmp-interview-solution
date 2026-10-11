package io.bifri.interview.cmp.padel.feature.player.ui.players

import io.bifri.interview.cmp.padel.feature.player.domain.model.Player

internal sealed interface PlayersPartialState {
    data object DataLoading : PlayersPartialState
    data class DataLoaded(val players: List<Player>) : PlayersPartialState
    data class DataLoadingError(val error: Throwable) : PlayersPartialState
    data class NameQueryChanged(val query: String) : PlayersPartialState
    data class Navigate(val navigationAction: PlayersNavigationAction) : PlayersPartialState
}
