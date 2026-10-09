package io.bifri.interview.cmp.padel.feature.match.ui.matches

import io.bifri.interview.cmp.padel.feature.match.domain.model.TournamentMatch

internal sealed interface MatchesPartialState {
    data object Loading : MatchesPartialState
    data class MatchesLoaded(val matches: List<TournamentMatch>) : MatchesPartialState
    data class LoadingFailed(val error: Throwable) : MatchesPartialState
    data class Navigate(val navigationAction: MatchesNavigationAction) : MatchesPartialState
}
