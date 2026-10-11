package io.bifri.interview.cmp.padel.feature.player.ui.player

internal sealed interface PlayerNavigationAction {
    data class ShowError(val error: Throwable) : PlayerNavigationAction
}
