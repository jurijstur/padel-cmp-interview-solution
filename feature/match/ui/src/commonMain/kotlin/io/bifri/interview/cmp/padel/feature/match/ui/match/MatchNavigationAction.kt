package io.bifri.interview.cmp.padel.feature.match.ui.match

internal sealed interface MatchNavigationAction {
    data class ShowError(val error: Throwable) : MatchNavigationAction
}
