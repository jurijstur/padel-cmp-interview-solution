package io.bifri.interview.cmp.padel.feature.match.ui.matches

internal sealed interface MatchesNavigationAction {
    data class OpenMatch(val matchId: Long, val totalCourts: Int?) : MatchesNavigationAction
    data class ShowError(val error: Throwable) : MatchesNavigationAction
}
