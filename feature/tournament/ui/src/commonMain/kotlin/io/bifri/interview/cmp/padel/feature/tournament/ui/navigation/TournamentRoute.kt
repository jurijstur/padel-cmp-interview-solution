package io.bifri.interview.cmp.padel.feature.tournament.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface TournamentRoute : NavKey {
    @Serializable
    data object Tournaments : TournamentRoute

    @Serializable
    data class Tournament(val tournamentId: Long) : TournamentRoute
}
