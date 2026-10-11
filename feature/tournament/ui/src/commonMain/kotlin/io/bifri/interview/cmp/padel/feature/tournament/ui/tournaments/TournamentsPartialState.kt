package io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments

import io.bifri.interview.cmp.padel.feature.tournament.domain.location.TournamentWithDistance

internal sealed interface TournamentsPartialState {
    data object DataLoading : TournamentsPartialState

    data class DataLoaded(
        val items: List<TournamentWithDistance>,
        val isLocationUnavailable: Boolean,
    ) : TournamentsPartialState

    data class DataLoadingError(val error: Throwable) : TournamentsPartialState

    data class Navigate(val navigationAction: TournamentsNavigationAction) : TournamentsPartialState
}
