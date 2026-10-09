package io.bifri.interview.cmp.padel.feature.match.ui.matches

import io.bifri.interview.cmp.padel.core.ui.Event
import io.bifri.interview.cmp.padel.feature.match.domain.model.TournamentMatch

internal data class MatchesState(
    val uiState: UiState = UiState(),
    val event: Event<MatchesNavigationAction>? = null,
) {
    data class UiState(
        val items: List<TournamentMatch> = emptyList(),
        val isDataLoading: Boolean = false,
        val tournamentName: String? = null,
    )
}
