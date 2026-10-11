package io.bifri.interview.cmp.padel.feature.player.ui.player

import androidx.lifecycle.viewModelScope
import io.bifri.interview.cmp.padel.core.coroutines.extension.mutableSharedFlowOfInput
import io.bifri.interview.cmp.padel.core.coroutines.extension.stateInViewModel
import io.bifri.interview.cmp.padel.core.exception.ExceptionHandler
import io.bifri.interview.cmp.padel.core.ui.Event
import io.bifri.interview.cmp.padel.core.ui.base.BaseViewModel
import io.bifri.interview.cmp.padel.feature.player.domain.exception.PlayerNotFoundException
import io.bifri.interview.cmp.padel.feature.player.domain.usecase.GetPlayerUseCase
import io.bifri.interview.cmp.padel.feature.player.ui.player.PlayerNavigationAction.ShowError
import io.bifri.interview.cmp.padel.feature.player.ui.player.PlayerPartialState.DataLoaded
import io.bifri.interview.cmp.padel.feature.player.ui.player.PlayerPartialState.DataLoading
import io.bifri.interview.cmp.padel.feature.player.ui.player.PlayerPartialState.DataLoadingError
import io.bifri.interview.cmp.padel.feature.player.ui.player.PlayerPartialState.Navigate
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

class PlayerViewModel(
    private val playerId: Long,
    coroutineDispatcher: CoroutineDispatcher,
    exceptionHandler: ExceptionHandler,
    private val getPlayerUseCase: GetPlayerUseCase,
) : BaseViewModel(coroutineDispatcher, exceptionHandler) {
    private val refreshes = mutableSharedFlowOfInput<Unit>()
    private val state: Flow<PlayerState> = _state
    internal val uiState = state.map { it.uiState }
    internal val events = state.mapNotNull { it.event }

    internal fun onRefresh() {
        refreshes.tryEmit(Unit)
    }

    private val _state: Flow<PlayerState>
        get() = merge(
            dataStream(),
        )
            .catch {
                exceptionHandler.handleException(it)
                emit(Navigate(ShowError(it)))
            }
            .runningFold(PlayerState(), ::reducePlayerState)
            .flowOn(coroutineDispatcher)
            .stateInViewModel(
                scope = viewModelScope,
                initialValue = PlayerState(),
            )

    private fun dataStream() = refreshes
        .onStart { emit(Unit) }
        .flatMapLatest {
            flow { emit(getPlayerUseCase(playerId)) }
                .map(::DataLoaded)
                .onStart<PlayerPartialState> { emit(DataLoading) }
                .catch {
                    exceptionHandler.handleException(it)
                    emit(DataLoadingError(it))
                }
        }
}

internal fun reducePlayerState(
    prevState: PlayerState,
    changes: PlayerPartialState,
): PlayerState = when (changes) {
    DataLoading -> prevState.copy(uiState = prevState.uiState.copy(isDataLoading = true))

    is DataLoadingError -> {
        val notFound = changes.error is PlayerNotFoundException
        prevState.copy(
            uiState = prevState.uiState.copy(
                isDataLoading = false,
                isDataLoadError = !notFound,
                isPlayerNotFound = notFound,
            ),
            event = if (notFound) null else Event(ShowError(changes.error)),
        )
    }

    is DataLoaded -> prevState.copy(
        uiState = prevState.uiState.copy(
            player = changes.player,
            isDataLoading = false,
            isDataLoadError = false,
            isPlayerNotFound = false,
        ),
    )

    is Navigate -> prevState.copy(event = Event(changes.navigationAction))
}
