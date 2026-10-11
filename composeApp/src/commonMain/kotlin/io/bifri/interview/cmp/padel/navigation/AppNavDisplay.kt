package io.bifri.interview.cmp.padel.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.defaultPopTransitionSpec
import io.bifri.interview.cmp.padel.core.ui.navigation.pop
import io.bifri.interview.cmp.padel.feature.match.ui.navigation.MatchNavDisplay
import io.bifri.interview.cmp.padel.feature.player.ui.player.PlayerRoute
import io.bifri.interview.cmp.padel.feature.player.ui.players.PlayersRoute
import io.bifri.interview.cmp.padel.feature.tournament.ui.navigation.TournamentNavDisplay
import io.bifri.interview.cmp.padel.navigation.AppRoute.MatchGraph
import io.bifri.interview.cmp.padel.navigation.AppRoute.PlayerDetail
import io.bifri.interview.cmp.padel.navigation.AppRoute.Players
import io.bifri.interview.cmp.padel.navigation.AppRoute.TournamentGraph

@Composable
fun AppNavDisplay(
    backStack: NavBackStack<NavKey>,
    onError: (Throwable) -> Unit,
) {
    NavDisplay(
        backStack = backStack,
        predictivePopTransitionSpec = { defaultPopTransitionSpec<NavKey>()() },
        entryProvider = entryProvider {
            entry<TournamentGraph> {
                TournamentNavDisplay(
                    onMatchesClick = { tournamentId, tournamentName ->
                        backStack.add(MatchGraph(tournamentId, tournamentName))
                    },
                    onPlayersClick = { backStack.add(Players) },
                    onError = onError,
                )
            }
            entry<Players> {
                PlayersRoute(
                    onBackClick = { backStack.pop() },
                    onPlayerClick = { backStack.add(PlayerDetail(playerId = it)) },
                    onError = onError,
                )
            }
            entry<MatchGraph> { route ->
                MatchNavDisplay(
                    tournamentId = route.tournamentId,
                    tournamentName = route.tournamentName,
                    onBackClick = { backStack.pop() },
                    onPlayerClick = { backStack.add(PlayerDetail(playerId = it)) },
                    onError = onError,
                )
            }
            entry<PlayerDetail> { route ->
                PlayerRoute(
                    playerId = route.playerId,
                    onBackClick = { backStack.pop() },
                    onError = onError,
                )
            }
        },
    )
}
