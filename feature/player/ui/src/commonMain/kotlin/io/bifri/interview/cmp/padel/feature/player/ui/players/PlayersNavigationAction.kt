package io.bifri.interview.cmp.padel.feature.player.ui.players

internal sealed interface PlayersNavigationAction {
    data class ShowError(val error: Throwable) : PlayersNavigationAction
}
