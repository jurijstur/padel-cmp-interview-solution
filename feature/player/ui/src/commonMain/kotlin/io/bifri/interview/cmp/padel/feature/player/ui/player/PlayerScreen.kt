package io.bifri.interview.cmp.padel.feature.player.ui.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.plus
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.layout.ContentScale
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
import io.bifri.interview.cmp.padel.feature.player.domain.model.PlayerDetails
import io.bifri.interview.cmp.padel.feature.player.ui.PlayerUiRes
import io.bifri.interview.cmp.padel.feature.player.ui.player.PlayerNavigationAction.ShowError
import io.bifri.interview.cmp.padel.feature.player.ui.player.PlayerState.UiState
import io.bifri.interview.cmp.padel.feature.player.ui.player_age
import io.bifri.interview.cmp.padel.feature.player.ui.player_birthplace
import io.bifri.interview.cmp.padel.feature.player.ui.player_category
import io.bifri.interview.cmp.padel.feature.player.ui.player_country
import io.bifri.interview.cmp.padel.feature.player.ui.player_elo
import io.bifri.interview.cmp.padel.feature.player.ui.player_hand
import io.bifri.interview.cmp.padel.feature.player.ui.player_height
import io.bifri.interview.cmp.padel.feature.player.ui.player_height_cm
import io.bifri.interview.cmp.padel.feature.player.ui.player_load_error
import io.bifri.interview.cmp.padel.feature.player.ui.player_not_found
import io.bifri.interview.cmp.padel.feature.player.ui.player_photo_cd
import io.bifri.interview.cmp.padel.feature.player.ui.player_points
import io.bifri.interview.cmp.padel.feature.player.ui.player_ranking
import io.bifri.interview.cmp.padel.feature.player.ui.player_side
import io.bifri.interview.cmp.padel.feature.player.ui.players_unranked
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

private val PlayerPhotoSize = 160.dp

@Composable
fun PlayerRoute(
    playerId: Long,
    onBackClick: () -> Unit,
    onError: (Throwable) -> Unit,
    viewModel: PlayerViewModel = koinViewModel(key = playerId.toString()) { parametersOf(playerId) },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(null)

    PlayerScreen(
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
private fun PlayerScreen(
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
                title = { Text(uiState.player?.name.orEmpty()) },
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
            val contentModifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding.plus(PaddingValues(all = dimens.margin)))
            val player = uiState.player
            when {
                player != null -> PlayerContent(
                    player = player,
                    modifier = contentModifier,
                )

                uiState.isPlayerNotFound -> PlayerMessage(
                    message = stringResource(PlayerUiRes.string.player_not_found),
                    modifier = contentModifier,
                )

                uiState.isDataLoadError -> PlayerMessage(
                    message = stringResource(PlayerUiRes.string.player_load_error),
                    modifier = contentModifier,
                )

                else -> Box(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun PlayerMessage(
    message: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun PlayerContent(
    player: PlayerDetails,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimens.spacingMedium),
    ) {
        PlayerPhoto(player)
        PlayerDetailsCard(
            player = player,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun PlayerPhoto(player: PlayerDetails) {
    val photoUrl = player.photoUrl?.takeIf { it.isNotBlank() } ?: return

    SubcomposeAsyncImage(
        model = photoUrl,
        contentDescription = stringResource(PlayerUiRes.string.player_photo_cd, player.name),
        modifier = Modifier.size(PlayerPhotoSize).clip(CircleShape),
        contentScale = ContentScale.Crop,
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

@Composable
private fun PlayerDetailsCard(
    player: PlayerDetails,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(dimens.cardPaddingMedium),
            verticalArrangement = Arrangement.spacedBy(dimens.spacingSmall),
        ) {
            DetailField(
                label = PlayerUiRes.string.player_ranking,
                value = player.ranking?.takeIf { it.isNotBlank() }
                    ?: stringResource(PlayerUiRes.string.players_unranked),
            )
            DetailField(
                label = PlayerUiRes.string.player_country,
                value = player.nationality?.takeIf { it.isNotBlank() },
            )
            DetailField(
                label = PlayerUiRes.string.player_points,
                value = player.points?.toString(),
            )
            DetailField(
                label = PlayerUiRes.string.player_elo,
                value = player.elo?.toString(),
            )
            DetailField(
                label = PlayerUiRes.string.player_category,
                value = player.category.labeled(),
            )
            DetailField(
                label = PlayerUiRes.string.player_side,
                value = player.side.labeled(),
            )
            DetailField(
                label = PlayerUiRes.string.player_hand,
                value = player.hand.labeled(),
            )
            DetailField(
                label = PlayerUiRes.string.player_height,
                value = player.height?.let {
                    stringResource(PlayerUiRes.string.player_height_cm, it.toString())
                },
            )
            DetailField(
                label = PlayerUiRes.string.player_birthplace,
                value = player.birthplace?.takeIf { it.isNotBlank() },
            )
            DetailField(
                label = PlayerUiRes.string.player_age,
                value = player.age?.takeIf { it.isNotBlank() },
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

private fun String?.labeled(): String? = this
    ?.takeIf { it.isNotBlank() }
    ?.replaceFirstChar { it.uppercase() }

private fun processEvents(event: Event<PlayerNavigationAction>, onError: (Throwable) -> Unit) {
    val action = event.getContentIfNotHandled() ?: return
    when (action) {
        is ShowError -> onError(action.error)
    }
}

@Preview(name = "Player")
@Composable
private fun PlayerScreenPreview() {
    AppTheme {
        PlayerScreen(
            uiState = UiState(
                player = previewPlayer(),
                isDataLoading = false,
            ),
            onBackClick = {},
            onRefresh = {},
        )
    }
}

@Preview(name = "Player not found")
@Composable
private fun PlayerScreenNotFoundPreview() {
    AppTheme {
        PlayerScreen(
            uiState = UiState(isPlayerNotFound = true),
            onBackClick = {},
            onRefresh = {},
        )
    }
}

@Preview(name = "Player load error")
@Composable
private fun PlayerScreenErrorPreview() {
    AppTheme {
        PlayerScreen(
            uiState = UiState(isDataLoadError = true),
            onBackClick = {},
            onRefresh = {},
        )
    }
}

private fun previewPlayer() = PlayerDetails(
    id = 1L,
    name = "Arturo Coello",
    photoUrl = null,
    category = "men",
    nationality = "ES",
    ranking = "1",
    points = 14280,
    elo = 2140,
    height = 190,
    side = "drive",
    hand = "right",
    birthplace = "Madrid",
    age = "24",
)
