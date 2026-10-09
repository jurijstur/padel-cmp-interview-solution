package io.bifri.interview.cmp.padel.feature.tournament.ui.tournament

import io.bifri.interview.cmp.padel.core.ui.Event
import io.bifri.interview.cmp.padel.feature.tournament.domain.model.Tournament

internal data class TournamentState(
    val uiState: UiState = UiState(),
    val event: Event<TournamentNavigationAction>? = null,
) {
    data class UiState(
        val tournament: Tournament? = null,
        val isDataLoading: Boolean = false,
    )
}
