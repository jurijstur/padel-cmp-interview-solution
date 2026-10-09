package io.bifri.interview.cmp.padel.feature.match.ui.match

import io.bifri.interview.cmp.padel.feature.match.domain.model.Match

internal sealed interface MatchPartialState {
    data object DataLoading : MatchPartialState
    data class DataLoaded(val match: Match) : MatchPartialState
    data class DataLoadingError(val error: Throwable) : MatchPartialState
    data class Navigate(val navigationAction: MatchNavigationAction) : MatchPartialState
}
