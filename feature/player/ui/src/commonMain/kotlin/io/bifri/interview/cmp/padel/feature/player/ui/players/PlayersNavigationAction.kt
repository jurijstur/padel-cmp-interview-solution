package io.bifri.interview.cmp.padel.feature.player.ui.players

internal sealed interface PlayersNavigationAction {
    data class OpenPlayer(val playerId: Long) : PlayersNavigationAction
    data class ShowError(val error: Throwable) : PlayersNavigationAction
}
