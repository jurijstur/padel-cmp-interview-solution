package io.bifri.interview.cmp.padel.feature.tournament.ui.tournament

internal sealed interface TournamentNavigationAction {
    data class OpenMatches(val tournamentName: String) : TournamentNavigationAction
    data class ShowError(val error: Throwable) : TournamentNavigationAction
}
