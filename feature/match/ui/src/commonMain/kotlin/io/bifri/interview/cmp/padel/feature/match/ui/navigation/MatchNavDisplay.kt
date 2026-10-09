package io.bifri.interview.cmp.padel.feature.match.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.defaultPopTransitionSpec
import androidx.savedstate.serialization.SavedStateConfiguration
import io.bifri.interview.cmp.padel.core.ui.navigation.pop
import io.bifri.interview.cmp.padel.feature.match.ui.match.MatchRoute
import io.bifri.interview.cmp.padel.feature.match.ui.matches.MatchesRoute
import io.bifri.interview.cmp.padel.feature.match.ui.navigation.MatchRoute.Match
import io.bifri.interview.cmp.padel.feature.match.ui.navigation.MatchRoute.Matches
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

private val MatchNavConfiguration = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(baseClass = NavKey::class) {
            subclass(Matches::class)
            subclass(Match::class)
        }
    }
}

@Composable
fun MatchNavDisplay(
    tournamentId: Long,
    tournamentName: String,
    onBackClick: () -> Unit,
    onError: (Throwable) -> Unit,
) {
    val backStack = rememberNavBackStack(
        MatchNavConfiguration,
        Matches(
            tournamentId = tournamentId,
            tournamentName = tournamentName,
        ),
    )

    NavDisplay(
        backStack = backStack,
        predictivePopTransitionSpec = { defaultPopTransitionSpec<NavKey>()() },
        entryProvider = entryProvider {
            entry<Matches> { route ->
                MatchesRoute(
                    tournamentId = route.tournamentId,
                    tournamentName = route.tournamentName,
                    onBackClick = onBackClick,
                    onMatchClick = { matchId, totalCourts ->
                        backStack.add(
                            Match(
                                matchId = matchId,
                                tournamentName = route.tournamentName,
                                totalCourts = totalCourts,
                            ),
                        )
                    },
                    onError = onError,
                )
            }
            entry<Match> { route ->
                MatchRoute(
                    matchId = route.matchId,
                    tournamentName = route.tournamentName,
                    totalCourts = route.totalCourts,
                    onBackClick = { backStack.pop() },
                    onError = onError,
                )
            }
        },
    )
}
