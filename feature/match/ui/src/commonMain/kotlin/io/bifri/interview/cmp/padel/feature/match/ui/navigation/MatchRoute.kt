package io.bifri.interview.cmp.padel.feature.match.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface MatchRoute : NavKey {
    @Serializable
    data class Matches(
        val tournamentId: Long,
        val tournamentName: String,
    ) : MatchRoute

    @Serializable
    data class Match(
        val matchId: Long,
        val tournamentName: String,
        val totalCourts: Int?,
    ) : MatchRoute
}
