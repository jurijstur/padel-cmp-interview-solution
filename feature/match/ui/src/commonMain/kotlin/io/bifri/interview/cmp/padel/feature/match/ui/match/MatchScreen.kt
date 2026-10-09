package io.bifri.interview.cmp.padel.feature.match.ui.match

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.plus
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.SubcomposeAsyncImage
import io.bifri.interview.cmp.padel.core.designsystem.component.appbar.CollapsingTopAppBar
import io.bifri.interview.cmp.padel.core.designsystem.component.button.BackIconButton
import io.bifri.interview.cmp.padel.core.designsystem.component.placeholder.ShimmerBox
import io.bifri.interview.cmp.padel.core.designsystem.component.pulltorefresh.AppPullToRefreshBox
import io.bifri.interview.cmp.padel.core.designsystem.component.scaffold.AppScaffold
import io.bifri.interview.cmp.padel.core.designsystem.theme.AppTheme
import io.bifri.interview.cmp.padel.core.designsystem.theme.dimens
import io.bifri.interview.cmp.padel.core.ui.Event
import io.bifri.interview.cmp.padel.core.ui.LifecycleCollectEffect
import io.bifri.interview.cmp.padel.feature.match.domain.model.Match
import io.bifri.interview.cmp.padel.feature.match.domain.model.Match.SetScore
import io.bifri.interview.cmp.padel.feature.match.domain.model.Match.Team
import io.bifri.interview.cmp.padel.feature.match.domain.model.Match.TeamIndex.TeamOne
import io.bifri.interview.cmp.padel.feature.match.domain.model.Match.TeamIndex.TeamTwo
import io.bifri.interview.cmp.padel.feature.match.ui.MatchUiRes
import io.bifri.interview.cmp.padel.feature.match.ui.match.MatchNavigationAction.ShowError
import io.bifri.interview.cmp.padel.feature.match.ui.match.MatchState.UiState
import io.bifri.interview.cmp.padel.feature.match.ui.match_court
import io.bifri.interview.cmp.padel.feature.match.ui.match_court_of_total
import io.bifri.interview.cmp.padel.feature.match.ui.match_player_avatar_cd
import io.bifri.interview.cmp.padel.feature.match.ui.match_sets
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

private val PlayerAvatarSize = 48.dp
private val ScoreColumnMinWidth = 32.dp

@Composable
fun MatchRoute(
    matchId: Long,
    tournamentName: String,
    totalCourts: Int?,
    onBackClick: () -> Unit,
    onError: (Throwable) -> Unit,
    viewModel: MatchViewModel = koinViewModel(key = matchId.toString()) {
        parametersOf(matchId, tournamentName, totalCourts)
    },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(null)

    MatchScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onRefresh = viewModel::onRefresh,
    )

    LifecycleCollectEffect(
        stream = viewModel.events,
        collector = { processEvents(it, onError) },
    )
}

@Composable
private fun MatchScreen(
    uiState: UiState?,
    onBackClick: () -> Unit,
    onRefresh: () -> Unit,
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
                        Text(text = uiState.match?.roundName.orEmpty())
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

            MatchContent(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(contentPadding),
                match = uiState.match,
                totalCourts = uiState.totalCourts,
            )
        }
    }
}

@Composable
private fun MatchContent(
    match: Match?,
    totalCourts: Int?,
    modifier: Modifier = Modifier,
) {
    match ?: return

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimens.spacingMedium),
    ) {
        Court(
            courtName = match.courtName,
            totalCourts = totalCourts,
        )
        ScoreboardCard(match = match)
    }
}

@Composable
private fun Court(
    courtName: String?,
    totalCourts: Int?,
) {
    val courtLabel = when {
        courtName != null && totalCourts != null ->
            stringResource(MatchUiRes.string.match_court_of_total, courtName, totalCourts)

        courtName != null -> courtName

        else -> null
    }
    if (courtLabel != null) {
        Column {
            Text(
                text = stringResource(MatchUiRes.string.match_court),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(text = courtLabel)
        }
    }
}

@Composable
private fun ScoreboardCard(match: Match) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(dimens.cardPaddingMedium),
            verticalArrangement = Arrangement.spacedBy(dimens.spacingSmall),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimens.spacingSmall),
            ) {
                Spacer(Modifier.width(PlayerAvatarSize))
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(MatchUiRes.string.match_sets),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                match.scores.forEach {
                    Text(
                        modifier = Modifier.widthIn(min = ScoreColumnMinWidth),
                        text = it.setNumber.toString(),
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            TeamScoreRow(
                team = match.teamOne,
                setGames = match.scores.map { it.teamOneGames },
                isWinner = match.winnerTeam == TeamOne,
            )
            HorizontalDivider()
            TeamScoreRow(
                team = match.teamTwo,
                setGames = match.scores.map { it.teamTwoGames },
                isWinner = match.winnerTeam == TeamTwo,
            )
        }
    }
}

@Composable
private fun TeamScoreRow(
    team: Team?,
    setGames: List<String?>,
    isWinner: Boolean,
) {
    team ?: return

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimens.spacingSmall),
    ) {
        team.players.forEachIndexed { index, player ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimens.spacingSmall),
            ) {
                val isFirstPlayer = index == 0
                val fontWeight = if (isWinner) FontWeight.Bold else FontWeight.Normal
                val color = if (isWinner) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface

                PlayerAvatar(
                    photoUrl = player.playerPhotoUrl,
                    contentDescription = stringResource(MatchUiRes.string.match_player_avatar_cd, player),
                )
                Text(
                    text = player.name.orEmpty(),
                    modifier = Modifier.weight(1f),
                    fontWeight = fontWeight,
                    color = color,
                )
                if (isFirstPlayer) {
                    setGames.forEach { games ->
                        Text(
                            modifier = Modifier.widthIn(min = ScoreColumnMinWidth),
                            text = games.toString(),
                            textAlign = TextAlign.End,
                            fontWeight = fontWeight,
                            color = color,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PlayerAvatar(photoUrl: String?, contentDescription: String) {
    SubcomposeAsyncImage(
        model = photoUrl,
        contentDescription = contentDescription,
        modifier = Modifier.size(PlayerAvatarSize).clip(CircleShape),
        contentScale = ContentScale.Fit,
        loading = { ShimmerBox(Modifier.matchParentSize()) },
        error = {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(MaterialTheme.colorScheme.surfaceVariant),
            )
        },
    )
}

private fun processEvents(event: Event<MatchNavigationAction>, onError: (Throwable) -> Unit) {
    val action = event.getContentIfNotHandled() ?: return
    when (action) {
        is ShowError -> onError(action.error)
    }
}

@Preview(name = "Match")
@Composable
private fun MatchScreenPreview() {
    AppTheme {
        MatchScreen(
            uiState = UiState(
                match = previewMatch(),
                tournamentName = "Madrid P1",
                totalCourts = 8,
            ),
            onBackClick = {},
            onRefresh = {},
        )
    }
}

@Preview(name = "Loading match")
@Composable
private fun MatchScreenLoadingPreview() {
    AppTheme {
        MatchScreen(
            uiState = UiState(
                isDataLoading = true,
                tournamentName = "Madrid P1",
                totalCourts = 8,
            ),
            onBackClick = {},
            onRefresh = {},
        )
    }
}

private fun previewMatch() = Match(
    id = 1L,
    courtName = "Center Court",
    roundName = "Semifinals",
    teamOne = Team(
        players = listOf(
            Team.Player(
                name = "Ozols",
                playerPhotoUrl = null,
            ),
            Team.Player(
                name = "Kalnins",
                playerPhotoUrl = null,
            ),
        ),
    ),
    teamTwo = Team(
        players = listOf(
            Team.Player(
                name = "Garcia",
                playerPhotoUrl = null,
            ),
            Team.Player(
                name = "Silva",
                playerPhotoUrl = null,
            ),
        ),
    ),
    scores = listOf(
        SetScore(
            setNumber = 1,
            teamOneGames = "6",
            teamTwoGames = "4",
        ),
        SetScore(
            setNumber = 2,
            teamOneGames = "6",
            teamTwoGames = "2",
        ),
    ),
    winnerTeam = TeamOne,
)
