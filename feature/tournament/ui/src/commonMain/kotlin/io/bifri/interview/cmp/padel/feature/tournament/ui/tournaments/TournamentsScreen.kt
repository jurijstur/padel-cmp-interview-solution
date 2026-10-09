package io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.plus
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.bifri.interview.cmp.padel.core.designsystem.component.appbar.CollapsingTopAppBar
import io.bifri.interview.cmp.padel.core.designsystem.component.pulltorefresh.AppPullToRefreshBox
import io.bifri.interview.cmp.padel.core.designsystem.component.scaffold.AppScaffold
import io.bifri.interview.cmp.padel.core.designsystem.theme.AppTheme
import io.bifri.interview.cmp.padel.core.designsystem.theme.dimens
import io.bifri.interview.cmp.padel.core.ui.Event
import io.bifri.interview.cmp.padel.core.ui.LifecycleCollectEffect
import io.bifri.interview.cmp.padel.feature.tournament.domain.model.TournamentsTournament
import io.bifri.interview.cmp.padel.feature.tournament.ui.TournamentUiRes
import io.bifri.interview.cmp.padel.feature.tournament.ui.component.TournamentSummary
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments.TournamentsNavigationAction.OpenTournament
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments.TournamentsNavigationAction.ShowError
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments.TournamentsState.UiState
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments_empty
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments_screen_title
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TournamentsRoute(
    onTournamentClick: (Long) -> Unit,
    onError: (Throwable) -> Unit,
    viewModel: TournamentsViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(null)

    TournamentsScreen(
        uiState = uiState,
        onRefresh = viewModel::onRefresh,
        onTournamentClick = viewModel::onTournamentClick,
    )

    LifecycleCollectEffect(
        stream = viewModel.events,
        collector = { processEvents(it, onTournamentClick, onError) },
    )
}

@Composable
private fun TournamentsScreen(
    uiState: UiState?,
    onRefresh: () -> Unit,
    onTournamentClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (uiState == null) return

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState(),
    )

    AppScaffold(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        topBar = {
            CollapsingTopAppBar(
                title = { Text(stringResource(TournamentUiRes.string.tournaments_screen_title)) },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        AppPullToRefreshBox(
            isRefreshing = uiState.isDataLoading,
            onRefresh = onRefresh,
            topPadding = innerPadding.calculateTopPadding(),
        ) {
            val contentPadding = innerPadding.plus(PaddingValues(all = dimens.margin))

            when {
                (uiState.items.isEmpty() && !uiState.isDataLoading) -> EmptyTournaments(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(contentPadding),
                )

                else -> TournamentsContent(
                    modifier = Modifier.fillMaxSize(),
                    items = uiState.items,
                    contentPadding = contentPadding,
                    onTournamentClick = onTournamentClick,
                )
            }
        }
    }
}

@Composable
private fun EmptyTournaments(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(text = stringResource(TournamentUiRes.string.tournaments_empty))
    }
}

@Composable
private fun TournamentsContent(
    items: List<TournamentsTournament>,
    contentPadding: PaddingValues,
    onTournamentClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimens.spacingMedium),
    ) {
        items(
            items = items,
            key = { it.id },
        ) { tournament ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onTournamentClick(tournament.id) },
            ) {
                TournamentSummary(
                    modifier = modifier.padding(dimens.cardPaddingMedium),
                    tournament = tournament,
                )
            }
        }
    }
}

private fun processEvents(
    event: Event<TournamentsNavigationAction>,
    onTournamentClick: (Long) -> Unit,
    onError: (Throwable) -> Unit,
) {
    val action = event.getContentIfNotHandled() ?: return
    when (action) {
        is OpenTournament -> onTournamentClick(action.tournamentId)
        is ShowError -> onError(action.error)
    }
}

@Preview(name = "Tournaments")
@Composable
private fun TournamentsScreenPreview() {
    AppTheme {
        TournamentsScreen(
            uiState = UiState(
                items = listOf(
                    previewTournament(
                        id = 740L,
                        name = "London P1 2026",
                    ),
                    previewTournament(
                        id = 827L,
                        name = "FIP Silver Bali Island Sports",
                    ),
                ),
                isDataLoading = false,
            ),
            onTournamentClick = {},
            onRefresh = {},
        )
    }
}

@Preview(name = "Loading tournaments")
@Composable
private fun TournamentsScreenLoadingPreview() {
    AppTheme {
        TournamentsScreen(
            uiState = UiState(isDataLoading = true),
            onTournamentClick = {},
            onRefresh = {},
        )
    }
}

@Preview(name = "Empty tournaments")
@Composable
private fun TournamentsScreenEmptyPreview() {
    AppTheme {
        TournamentsScreen(
            uiState = UiState(
                items = emptyList(),
                isDataLoading = false,
            ),
            onTournamentClick = {},
            onRefresh = {},
        )
    }
}

@Suppress("MagicNumber")
private fun previewTournament(id: Long, name: String) = TournamentsTournament(
    id = id,
    name = name,
    location = "London",
    country = "GB",
    level = "p1",
    status = "finished",
    startDate = LocalDate(2026, 8, 3),
    endDate = LocalDate(2026, 8, 9),
)
