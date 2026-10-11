package io.bifri.interview.cmp.padel.feature.player.ui.players

import androidx.lifecycle.viewModelScope
import com.hoc081098.flowext.throttleTime
import io.bifri.interview.cmp.padel.core.coroutines.extension.mutableSharedFlowOfInput
import io.bifri.interview.cmp.padel.core.coroutines.extension.stateInViewModel
import io.bifri.interview.cmp.padel.core.exception.ExceptionHandler
import io.bifri.interview.cmp.padel.core.ui.Event
import io.bifri.interview.cmp.padel.core.ui.base.BaseViewModel
import io.bifri.interview.cmp.padel.core.ui.input.InputThrottle.DefaultSkipDuration
import io.bifri.interview.cmp.padel.feature.player.domain.usecase.GetPlayersUseCase
import io.bifri.interview.cmp.padel.feature.player.ui.players.PlayersNavigationAction.OpenPlayer
import io.bifri.interview.cmp.padel.feature.player.ui.players.PlayersNavigationAction.ShowError
import io.bifri.interview.cmp.padel.feature.player.ui.players.PlayersPartialState.DataLoaded
import io.bifri.interview.cmp.padel.feature.player.ui.players.PlayersPartialState.DataLoading
import io.bifri.interview.cmp.padel.feature.player.ui.players.PlayersPartialState.DataLoadingError
import io.bifri.interview.cmp.padel.feature.player.ui.players.PlayersPartialState.NameQueryChanged
import io.bifri.interview.cmp.padel.feature.player.ui.players.PlayersPartialState.Navigate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.runningFold

class PlayersViewModel(
    coroutineDispatcher: CoroutineDispatcher,
    exceptionHandler: ExceptionHandler,
    private val getPlayersUseCase: GetPlayersUseCase,
) : BaseViewModel(coroutineDispatcher, exceptionHandler) {
    private val refreshes = mutableSharedFlowOfInput<Unit>()
    private val nameQueries = mutableSharedFlowOfInput<String>()
    private val playerClicks = mutableSharedFlowOfInput<Long>()
    private val state: Flow<PlayersState> = _state
    internal val uiState = state.map { it.uiState }
    internal val events = state.mapNotNull { it.event }

    internal fun onRefresh() {
        refreshes.tryEmit(Unit)
    }

    internal fun onNameQueryChange(query: String) {
        nameQueries.tryEmit(query)
    }

    internal fun onPlayerClick(playerId: Long) {
        playerClicks.tryEmit(playerId)
    }

    private val _state: Flow<PlayersState>
        get() = merge(
            dataStream(),
            nameQueryStream(),
            playerClicksStream(),
        )
            .catch {
                exceptionHandler.handleException(it)
                emit(Navigate(ShowError(it)))
            }
            .runningFold(PlayersState(), ::reducePlayersState)
            .flowOn(coroutineDispatcher)
            .stateInViewModel(
                scope = viewModelScope,
                initialValue = PlayersState(),
            )

    private fun dataStream() = refreshes
        .onStart { emit(Unit) }
        .flatMapLatest {
            flow { emit(getPlayersUseCase()) }
                .map(::DataLoaded)
                .onStart<PlayersPartialState> { emit(DataLoading) }
                .catch {
                    exceptionHandler.handleException(it)
                    emit(DataLoadingError(it))
                }
        }

    private fun nameQueryStream(): Flow<PlayersPartialState> = nameQueries.map(::NameQueryChanged)

    private fun playerClicksStream(): Flow<PlayersPartialState> = playerClicks
        .throttleTime(DefaultSkipDuration)
        .map { Navigate(OpenPlayer(it)) }
}

internal fun reducePlayersState(
    prevState: PlayersState,
    changes: PlayersPartialState,
): PlayersState = when (changes) {
    DataLoading -> prevState.copy(uiState = prevState.uiState.copy(isDataLoading = true))

    is DataLoadingError -> prevState.copy(
        uiState = prevState.uiState.copy(
            isDataLoading = false,
            isDataLoadError = true,
        ),
        event = Event(ShowError(changes.error)),
    )

    is DataLoaded -> prevState.copy(
        uiState = prevState.uiState.copy(
            players = changes.players,
            visiblePlayers = filterPlayersByName(changes.players, prevState.uiState.nameQuery),
            isDataLoading = false,
            isDataLoadError = false,
        ),
    )

    is NameQueryChanged -> prevState.copy(
        uiState = prevState.uiState.copy(
            nameQuery = changes.query,
            visiblePlayers = filterPlayersByName(prevState.uiState.players, changes.query),
        ),
    )

    is Navigate -> prevState.copy(event = Event(changes.navigationAction))
}
