package io.bifri.interview.cmp.padel.feature.player.ui.players

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
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
import io.bifri.interview.cmp.padel.feature.player.domain.model.Player
import io.bifri.interview.cmp.padel.feature.player.ui.PlayerUiRes
import io.bifri.interview.cmp.padel.feature.player.ui.players.PlayersNavigationAction.OpenPlayer
import io.bifri.interview.cmp.padel.feature.player.ui.players.PlayersNavigationAction.ShowError
import io.bifri.interview.cmp.padel.feature.player.ui.players.PlayersState.UiState
import io.bifri.interview.cmp.padel.feature.player.ui.players_empty
import io.bifri.interview.cmp.padel.feature.player.ui.players_load_error
import io.bifri.interview.cmp.padel.feature.player.ui.players_screen_title
import io.bifri.interview.cmp.padel.feature.player.ui.players_search_clear_cd
import io.bifri.interview.cmp.padel.feature.player.ui.players_search_empty
import io.bifri.interview.cmp.padel.feature.player.ui.players_search_label
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

private const val LoadingSkeletonCount = 8
private val LoadingSkeletonHeight = 72.dp

@Composable
fun PlayersRoute(
    onBackClick: () -> Unit,
    onPlayerClick: (Long) -> Unit,
    onError: (Throwable) -> Unit,
    viewModel: PlayersViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(null)

    PlayersScreen(
        uiState = uiState,
        onRefresh = viewModel::onRefresh,
        onNameQueryChange = viewModel::onNameQueryChange,
        onPlayerClick = viewModel::onPlayerClick,
        onBackClick = onBackClick,
    )

    LifecycleCollectEffect(
        stream = viewModel.events,
        collector = { processEvents(it, onPlayerClick, onError) },
    )
}

@Composable
private fun PlayersScreen(
    uiState: UiState?,
    onRefresh: () -> Unit,
    onNameQueryChange: (String) -> Unit,
    onPlayerClick: (Long) -> Unit,
    onBackClick: () -> Unit,
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
                title = { Text(stringResource(PlayerUiRes.string.players_screen_title)) },
                navigationIcon = { BackIconButton(onClick = onBackClick) },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        val layoutDirection = LocalLayoutDirection.current
        // The top bar slides away on scroll. The search field stays on screen, so keep
        // at least the status-bar inset or the field draws under the iPhone status bar.
        val statusBarTop = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
        val topPadding = innerPadding.calculateTopPadding().coerceAtLeast(statusBarTop)
        AppPullToRefreshBox(
            isRefreshing = uiState.isDataLoading,
            onRefresh = onRefresh,
            topPadding = topPadding,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = topPadding,
                        start = innerPadding.calculateStartPadding(layoutDirection),
                        end = innerPadding.calculateEndPadding(layoutDirection),
                        bottom = innerPadding.calculateBottomPadding(),
                    )
                    .padding(all = dimens.margin),
                verticalArrangement = Arrangement.spacedBy(dimens.spacingMedium),
            ) {
                PlayerSearchField(
                    query = uiState.nameQuery,
                    onQueryChange = onNameQueryChange,
                )
                Box(modifier = Modifier.weight(1f)) {
                    when {
                        uiState.players.isEmpty() && uiState.isDataLoading -> LoadingPlayers(
                            modifier = Modifier.fillMaxSize(),
                        )

                        uiState.players.isEmpty() && uiState.isDataLoadError -> PlayersMessage(
                            message = stringResource(PlayerUiRes.string.players_load_error),
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState()),
                        )

                        uiState.visiblePlayers.isEmpty() -> PlayersMessage(
                            message = stringResource(
                                if (uiState.nameQuery.isBlank()) {
                                    PlayerUiRes.string.players_empty
                                } else {
                                    PlayerUiRes.string.players_search_empty
                                },
                            ),
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState()),
                        )

                        else -> PlayersContent(
                            players = uiState.visiblePlayers,
                            onPlayerClick = onPlayerClick,
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PlayerSearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    // iOS resets the cursor when this field is driven by a string from a flow.
    // Keep the selection here and send only the text on to the filter.
    var field by remember {
        mutableStateOf(TextFieldValue(query, TextRange(query.length)))
    }
    OutlinedTextField(
        value = field,
        onValueChange = { updated ->
            field = updated
            if (updated.text != query) onQueryChange(updated.text)
        },
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        label = { Text(stringResource(PlayerUiRes.string.players_search_label)) },
        trailingIcon = {
            if (field.text.isNotEmpty()) {
                IconButton(
                    onClick = {
                        field = TextFieldValue("")
                        onQueryChange("")
                    },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = stringResource(PlayerUiRes.string.players_search_clear_cd),
                    )
                }
            }
        },
    )
}

@Composable
private fun LoadingPlayers(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimens.spacingMedium),
    ) {
        repeat(LoadingSkeletonCount) {
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
private fun PlayersMessage(
    message: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(text = message)
    }
}

@Composable
private fun PlayersContent(
    players: List<Player>,
    onPlayerClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimens.spacingMedium),
    ) {
        items(
            items = players,
            key = { it.id },
        ) { player ->
            PlayerRow(
                player = player,
                onClick = { onPlayerClick(player.id) },
            )
        }
    }
}

private fun processEvents(
    event: Event<PlayersNavigationAction>,
    onPlayerClick: (Long) -> Unit,
    onError: (Throwable) -> Unit,
) {
    val action = event.getContentIfNotHandled() ?: return
    when (action) {
        is OpenPlayer -> onPlayerClick(action.playerId)
        is ShowError -> onError(action.error)
    }
}

@Preview(name = "Players")
@Composable
private fun PlayersScreenPreview() {
    AppTheme {
        val players = listOf(
            previewPlayer(id = 3L, name = "Alejandro Galan", ranking = "3"),
            previewPlayer(id = 1L, name = "Arturo Coello", ranking = "1"),
        )
        PlayersScreen(
            uiState = UiState(
                players = players,
                visiblePlayers = players,
                isDataLoading = false,
            ),
            onRefresh = {},
            onNameQueryChange = {},
            onPlayerClick = {},
            onBackClick = {},
        )
    }
}

@Preview(name = "Players search empty")
@Composable
private fun PlayersScreenSearchEmptyPreview() {
    AppTheme {
        PlayersScreen(
            uiState = UiState(
                players = listOf(previewPlayer(id = 1L, name = "Arturo Coello", ranking = "1")),
                nameQuery = "Galan",
                isDataLoading = false,
            ),
            onRefresh = {},
            onNameQueryChange = {},
            onPlayerClick = {},
            onBackClick = {},
        )
    }
}

private fun previewPlayer(id: Long, name: String, ranking: String) = Player(
    id = id,
    name = name,
    shortName = null,
    photoUrl = null,
    nationality = "ES",
    ranking = ranking,
    points = null,
    category = "men",
)
