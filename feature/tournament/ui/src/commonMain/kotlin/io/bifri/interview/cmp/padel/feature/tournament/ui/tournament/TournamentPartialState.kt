package io.bifri.interview.cmp.padel.feature.tournament.ui.tournament

import io.bifri.interview.cmp.padel.feature.tournament.domain.model.Tournament

internal sealed interface TournamentPartialState {
    data object DataLoading : TournamentPartialState
    data class DataLoaded(val tournament: Tournament) : TournamentPartialState
    data class DataLoadingError(val error: Throwable) : TournamentPartialState
    data class Navigate(val navigationAction: TournamentNavigationAction) : TournamentPartialState
}
