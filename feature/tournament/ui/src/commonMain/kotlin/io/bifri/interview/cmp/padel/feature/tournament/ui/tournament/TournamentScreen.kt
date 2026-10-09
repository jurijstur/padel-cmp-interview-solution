package io.bifri.interview.cmp.padel.feature.tournament.ui.tournament

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
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
import io.bifri.interview.cmp.padel.feature.tournament.domain.model.Tournament
import io.bifri.interview.cmp.padel.feature.tournament.domain.model.TournamentsTournament
import io.bifri.interview.cmp.padel.feature.tournament.ui.TournamentUiRes
import io.bifri.interview.cmp.padel.feature.tournament.ui.component.TournamentSummary
import io.bifri.interview.cmp.padel.feature.tournament.ui.detail_address
import io.bifri.interview.cmp.padel.feature.tournament.ui.detail_court
import io.bifri.interview.cmp.padel.feature.tournament.ui.detail_prize_money
import io.bifri.interview.cmp.padel.feature.tournament.ui.detail_timezone
import io.bifri.interview.cmp.padel.feature.tournament.ui.detail_venue
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournament.TournamentNavigationAction.OpenMatches
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournament.TournamentState.UiState
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournament_matches_button
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournament_poster_cd
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournament_screen_title
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun TournamentRoute(
    tournamentId: Long,
    onBackClick: () -> Unit,
    onMatchesClick: (tournamentName: String) -> Unit,
    onError: (Throwable) -> Unit,
    viewModel: TournamentViewModel = koinViewModel(key = tournamentId.toString()) { parametersOf(tournamentId) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(null)

    TournamentScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onMatchesClick = viewModel::onMatchesClick,
        onRefresh = viewModel::onRefresh,
    )

    LifecycleCollectEffect(
        stream = viewModel.events,
        collector = { processEvents(it, onMatchesClick, onError) },
    )
}

@Composable
private fun TournamentScreen(
    uiState: UiState?,
    onBackClick: () -> Unit,
    onMatchesClick: (tournamentName: String) -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (uiState == null) return

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState(),
    )
    val tournamentName = uiState.tournament?.name
    val title = when {
        uiState.tournament == null -> ""
        tournamentName != null -> tournamentName
        else -> stringResource(TournamentUiRes.string.tournament_screen_title)
    }

    AppScaffold(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        topBar = {
            CollapsingTopAppBar(
                title = { Text(title) },
                navigationIcon = { BackIconButton(onClick = onBackClick) },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        val layoutDirection = LocalLayoutDirection.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = innerPadding.calculateStartPadding(layoutDirection),
                    end = innerPadding.calculateEndPadding(layoutDirection),
                ),
        ) {
            Row(
                modifier = Modifier.padding(all = dimens.spacingMedium),
                horizontalArrangement = Arrangement.spacedBy(dimens.spacingSmall),
            ) {
                AssistChip(
                    onClick = { onMatchesClick(uiState.tournament?.name.orEmpty()) },
                    label = { Text(stringResource(TournamentUiRes.string.tournament_matches_button)) },
                )
            }
            AppPullToRefreshBox(
                isRefreshing = uiState.isDataLoading,
                onRefresh = onRefresh,
            ) {
                TournamentContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(
                            start = dimens.spacingMedium,
                            end = dimens.spacingMedium,
                            bottom = innerPadding.calculateBottomPadding().plus(dimens.spacingMedium),
                        ),
                    tournament = uiState.tournament,
                )
            }
        }
    }
}

@Composable
private fun TournamentContent(
    tournament: Tournament?,
    modifier: Modifier = Modifier,
) {
    tournament ?: return

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimens.spacingMedium),
    ) {
        tournament.photoUrl?.let {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(TournamentPosterHeight),
                model = it,
                contentDescription = stringResource(
                    TournamentUiRes.string.tournament_poster_cd,
                    tournament.name.orEmpty(),
                ),
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
        TournamentSummary(tournament.toTournamentsTournament())
        TournamentDetails(tournament)
    }
}

@Composable
private fun TournamentDetails(tournament: Tournament) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .padding(dimens.cardPaddingMedium),
            verticalArrangement = Arrangement.spacedBy(dimens.spacingSmall),
        ) {
            DetailField(
                label = TournamentUiRes.string.detail_venue,
                value = tournament.venueName,
            )
            DetailField(
                label = TournamentUiRes.string.detail_address,
                value = tournament.venueAddress,
            )
            DetailField(
                label = TournamentUiRes.string.detail_timezone,
                value = tournament.timezone,
            )
            DetailField(
                label = TournamentUiRes.string.detail_court,
                value = tournament.courtType?.replaceFirstChar { it.uppercase() },
            )
            DetailField(
                label = TournamentUiRes.string.detail_prize_money,
                value = tournament.prizeAmount?.let { "$it ${tournament.prizeCurrency.orEmpty()}" },
            )
        }
    }
}

@Composable
private fun DetailField(label: StringResource, value: String?) {
    value ?: return

    Column {
        Text(
            text = stringResource(label),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(text = value)
    }
}

private fun processEvents(
    event: Event<TournamentNavigationAction>,
    onMatchesClick: (String) -> Unit,
    onError: (Throwable) -> Unit,
) {
    val action = event.getContentIfNotHandled() ?: return
    when (action) {
        is OpenMatches -> onMatchesClick(action.tournamentName)
        is TournamentNavigationAction.ShowError -> onError(action.error)
    }
}

private fun Tournament.toTournamentsTournament() = TournamentsTournament(
    id = id,
    name = null,
    location = location,
    country = country,
    level = level,
    status = status,
    startDate = startDate,
    endDate = endDate,
)

private val TournamentPosterHeight = 320.dp

@Preview(name = "Tournament")
@Composable
private fun TournamentScreenPreview() {
    AppTheme {
        TournamentScreen(
            uiState = UiState(
                tournament = previewTournament(),
                isDataLoading = false,
            ),
            onBackClick = {},
            onMatchesClick = {},
            onRefresh = {},
        )
    }
}

@Preview(name = "Loading tournament")
@Composable
private fun TournamentScreenLoadingPreview() {
    AppTheme {
        TournamentScreen(
            uiState = UiState(
                tournament = null,
                isDataLoading = true,
            ),
            onBackClick = {},
            onMatchesClick = {},
            onRefresh = {},
        )
    }
}

@Suppress("MagicNumber")
private fun previewTournament() = Tournament(
    id = 734L,
    name = "Italy Major 2026",
    location = "Rome",
    country = "IT",
    timezone = "Europe/Rome",
    level = "major",
    status = "finished",
    venueName = "Parco Sportivo Foro Italico",
    venueAddress = "Viale Dei Gladiatori, 31 – 00135 – Roma, Italia",
    prizeAmount = 1_044_849,
    prizeCurrency = "EUR",
    photoUrl = "https://media.padelapi.org/tournament-api/italy-major-2026-30cb3641.webp",
    courtType = "outdoor",
    startDate = LocalDate(2026, 6, 1),
    endDate = LocalDate(2026, 6, 7),
)
