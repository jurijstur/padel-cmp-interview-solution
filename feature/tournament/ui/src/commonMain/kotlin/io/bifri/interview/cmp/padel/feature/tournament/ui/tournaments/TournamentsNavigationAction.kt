package io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments

internal sealed interface TournamentsNavigationAction {
    data class OpenTournament(val tournamentId: Long) : TournamentsNavigationAction
    data class ShowError(val error: Throwable) : TournamentsNavigationAction
}
