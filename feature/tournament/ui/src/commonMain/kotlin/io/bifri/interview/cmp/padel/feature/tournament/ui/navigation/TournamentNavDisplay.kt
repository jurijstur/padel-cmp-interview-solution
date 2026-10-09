package io.bifri.interview.cmp.padel.feature.tournament.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.defaultPopTransitionSpec
import androidx.savedstate.serialization.SavedStateConfiguration
import io.bifri.interview.cmp.padel.core.ui.navigation.pop
import io.bifri.interview.cmp.padel.feature.tournament.ui.navigation.TournamentRoute.Tournament
import io.bifri.interview.cmp.padel.feature.tournament.ui.navigation.TournamentRoute.Tournaments
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournament.TournamentRoute
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments.TournamentsRoute
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

private val TournamentNavConfiguration = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(baseClass = NavKey::class) {
            subclass(Tournaments::class)
            subclass(Tournament::class)
        }
    }
}

@Composable
fun TournamentNavDisplay(
    onMatchesClick: (tournamentId: Long, tournamentName: String) -> Unit,
    onError: (Throwable) -> Unit,
) {
    val backStack = rememberNavBackStack(TournamentNavConfiguration, Tournaments)

    NavDisplay(
        backStack = backStack,
        predictivePopTransitionSpec = { defaultPopTransitionSpec<NavKey>()() },
        entryProvider = entryProvider {
            entry<Tournaments> {
                TournamentsRoute(
                    onTournamentClick = { backStack.add(Tournament(tournamentId = it)) },
                    onError = onError,
                )
            }
            entry<Tournament> { route ->
                TournamentRoute(
                    tournamentId = route.tournamentId,
                    onBackClick = { backStack.pop() },
                    onMatchesClick = { name -> onMatchesClick(route.tournamentId, name) },
                    onError = onError,
                )
            }
        },
    )
}
