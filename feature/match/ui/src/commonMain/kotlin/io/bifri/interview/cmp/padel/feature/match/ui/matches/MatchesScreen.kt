@file:Suppress("TooManyFunctions")

package io.bifri.interview.cmp.padel.feature.match.ui.matches

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.plus
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.bifri.interview.cmp.padel.core.designsystem.component.appbar.CollapsingTopAppBar
import io.bifri.interview.cmp.padel.core.designsystem.component.button.BackIconButton
import io.bifri.interview.cmp.padel.core.designsystem.component.placeholder.ShimmerBox
import io.bifri.interview.cmp.padel.core.designsystem.component.pulltorefresh.AppPullToRefreshBox
import io.bifri.interview.cmp.padel.core.designsystem.component.scaffold.AppScaffold
import io.bifri.interview.cmp.padel.core.designsystem.theme.AppTheme
import io.bifri.interview.cmp.padel.core.designsystem.theme.dimens
import io.bifri.interview.cmp.padel.core.ui.Event
import io.bifri.interview.cmp.padel.core.ui.LifecycleCollectEffect
import io.bifri.interview.cmp.padel.feature.match.domain.model.TournamentMatch
import io.bifri.interview.cmp.padel.feature.match.domain.model.TournamentMatch.SetScore
import io.bifri.interview.cmp.padel.feature.match.domain.model.TournamentMatch.Team
import io.bifri.interview.cmp.padel.feature.match.domain.model.TournamentMatch.TeamIndex.TeamOne
import io.bifri.interview.cmp.padel.feature.match.domain.model.TournamentMatch.TeamIndex.TeamTwo
import io.bifri.interview.cmp.padel.feature.match.ui.MatchUiRes
import io.bifri.interview.cmp.padel.feature.match.ui.matches.MatchesNavigationAction.OpenMatch
import io.bifri.interview.cmp.padel.feature.match.ui.matches.MatchesNavigationAction.ShowError
import io.bifri.interview.cmp.padel.feature.match.ui.matches.MatchesState.UiState
import io.bifri.interview.cmp.padel.feature.match.ui.matches_empty
import io.bifri.interview.cmp.padel.feature.match.ui.matches_screen_title
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

private const val LoadingSkeletonCount = 7
private val LoadingSkeletonHeight = 120.dp

@Composable
fun MatchesRoute(
    tournamentId: Long,
    tournamentName: String,
    onBackClick: () -> Unit,
    onMatchClick: (Long, Int?) -> Unit,
    onError: (Throwable) -> Unit,
    viewModel: MatchesViewModel = koinViewModel(key = tournamentId.toString()) {
        parametersOf(tournamentId, tournamentName)
    },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(null)

    MatchesScreen(
        uiState = uiState,
        onRefresh = viewModel::onRefresh,
        onMatchClick = viewModel::onMatchClick,
        onBackClick = onBackClick,
    )

    LifecycleCollectEffect(
        stream = viewModel.events,
        collector = {
            processEvents(
                event = it,
                onMatchClick = onMatchClick,
                onError = onError,
            )
        },
    )
}

@Composable
private fun MatchesScreen(
    uiState: UiState?,
    onRefresh: () -> Unit,
    onMatchClick: (Long, Int?) -> Unit,
    onBackClick: () -> Unit = {},
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
                title = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(dimens.spacingExtraSmall),
                    ) {
                        Text(text = stringResource(MatchUiRes.string.matches_screen_title))
                        Text(
                            text = uiState.tournamentName.orEmpty(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                },
                navigationIcon = { BackIconButton(onClick = onBackClick) },
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
                uiState.isDataLoading && uiState.items.isEmpty() -> LoadingMatchesContent(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = contentPadding,
                )

                uiState.items.isEmpty() -> EmptyMatchesContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(contentPadding),
                )

                else -> MatchesContent(
                    modifier = Modifier.fillMaxSize(),
                    items = uiState.items,
                    contentPadding = contentPadding,
                    onMatchClick = onMatchClick,
                )
            }
        }
    }
}

@Composable
private fun LoadingMatchesContent(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimens.spacingMedium),
    ) {
        items(LoadingSkeletonCount) {
            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(LoadingSkeletonHeight)
                    .clip(MaterialTheme.shapes.small),
            )
        }
    }
}

@Composable
private fun EmptyMatchesContent(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(text = stringResource(MatchUiRes.string.matches_empty))
    }
}

@Composable
private fun MatchesContent(
    items: List<TournamentMatch>,
    contentPadding: PaddingValues,
    onMatchClick: (Long, Int?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val totalCourts = items.mapNotNull { it.courtOrder }.maxOfOrNull { it }

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(
            items = items,
            key = { it.id },
        ) { match ->
            MatchListItem(
                match = match,
                totalCourts = totalCourts,
                onMatchClick = onMatchClick,
            )
        }
    }
}

@Composable
private fun MatchListItem(
    match: TournamentMatch,
    totalCourts: Int?,
    onMatchClick: (Long, Int?) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = { onMatchClick(match.id, totalCourts) },
    ) {
        Column(
            modifier = Modifier.padding(dimens.cardPaddingMedium),
            verticalArrangement = Arrangement.spacedBy(dimens.spacingSmall),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(dimens.spacingMedium),
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = match.roundName.formatRoundName(),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = match.status.formatStatus(),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            Text(
                text = match.formatDateAndCourtName(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            TeamSummaryRow(
                teamName = match.teamOne?.name.orEmpty(),
                games = match.scores.joinToString(" ") { it.teamOneGames.toString() },
                isWinner = match.winnerTeam == TeamOne,
            )
            TeamSummaryRow(
                teamName = match.teamTwo?.name.orEmpty(),
                games = match.scores.joinToString(" ") { it.teamTwoGames.toString() },
                isWinner = match.winnerTeam == TeamTwo,
            )
        }
    }
}

@Composable
private fun TeamSummaryRow(teamName: String, games: String, isWinner: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(dimens.spacingMedium),
    ) {
        val fontWeight = if (isWinner) FontWeight.Bold else FontWeight.Normal
        val color = if (isWinner) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface

        Text(
            modifier = Modifier.weight(1f),
            text = teamName,
            fontWeight = fontWeight,
            color = color,
        )
        Text(
            text = games,
            fontWeight = fontWeight,
            color = color,
        )
    }
}

private fun String?.formatRoundName() = this?.uppercase().orEmpty()

private fun String?.formatStatus() = this?.replace('_', ' ')?.uppercase().orEmpty()

private fun TournamentMatch.formatDateAndCourtName() = listOfNotNull(
    playedAt ?: scheduledAt,
    courtName,
)
    .joinToString(", ")

private fun processEvents(
    event: Event<MatchesNavigationAction>,
    onMatchClick: (Long, Int?) -> Unit,
    onError: (Throwable) -> Unit,
) {
    val action = event.getContentIfNotHandled() ?: return
    when (action) {
        is OpenMatch -> onMatchClick(action.matchId, action.totalCourts)
        is ShowError -> onError(action.error)
    }
}

@Preview(name = "Matches")
@Composable
private fun MatchesScreenPreview() {
    AppTheme {
        MatchesScreen(
            uiState = UiState(
                items = listOf(
                    previewMatch(
                        id = 1L,
                        teamOneName = "Coello / Tapia",
                        teamTwoName = "Galan / Chingotto",
                    ),
                    previewMatch(
                        id = 2L,
                        teamOneName = "Lebron / Di Nenno",
                        teamTwoName = "Stupaczuk / Yanguas",
                    ),
                ),
                isDataLoading = false,
                tournamentName = "Madrid P1",
            ),
            onMatchClick = { _, _ -> },
            onRefresh = {},
        )
    }
}

@Preview(name = "Loading matches")
@Composable
private fun MatchesScreenLoadingPreview() {
    AppTheme {
        MatchesScreen(
            uiState = UiState(
                isDataLoading = true,
                tournamentName = "Madrid P1",
            ),
            onMatchClick = { _, _ -> },
            onRefresh = {},
        )
    }
}

@Preview(name = "Empty matches")
@Composable
private fun MatchesScreenEmptyPreview() {
    AppTheme {
        MatchesScreen(
            uiState = UiState(
                tournamentName = "Madrid P1",
            ),
            onMatchClick = { _, _ -> },
            onRefresh = {},
        )
    }
}

private fun previewMatch(
    id: Long,
    teamOneName: String,
    teamTwoName: String,
) = TournamentMatch(
    id = id,
    courtName = "Center Court",
    courtOrder = null,
    status = "finished",
    roundName = "Semifinals",
    teamOne = Team(name = teamOneName),
    teamTwo = Team(name = teamTwoName),
    scores = listOf(
        SetScore(
            setNumber = 1,
            teamOneGames = "6",
            teamTwoGames = "4",
        ),
        SetScore(
            setNumber = 2,
            teamOneGames = "3",
            teamTwoGames = "2",
        ),
    ),
    winnerTeam = TeamOne,
    playedAt = LocalDate.parse("2026-09-08"),
    scheduledAt = LocalDate.parse("2026-09-08"),
)
