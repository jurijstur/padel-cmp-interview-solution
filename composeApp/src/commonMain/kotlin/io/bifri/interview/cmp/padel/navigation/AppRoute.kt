package io.bifri.interview.cmp.padel.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute : NavKey {
    @Serializable
    data object TournamentGraph : AppRoute

    @Serializable
    data class MatchGraph(
        val tournamentId: Long,
        val tournamentName: String,
    ) : AppRoute
}
