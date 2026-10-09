package io.bifri.interview.cmp.padel.feature.match.ui.match

import io.bifri.interview.cmp.padel.core.ui.Event
import io.bifri.interview.cmp.padel.feature.match.domain.model.Match

internal data class MatchState(
    val uiState: UiState = UiState(),
    val event: Event<MatchNavigationAction>? = null,
) {
    data class UiState(
        val match: Match? = null,
        val isDataLoading: Boolean = false,
        val tournamentName: String? = null,
        val totalCourts: Int? = null,
    )
}
