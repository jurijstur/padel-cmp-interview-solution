package io.bifri.interview.cmp.padel.feature.match.ui.match

import androidx.lifecycle.viewModelScope
import io.bifri.interview.cmp.padel.core.coroutines.extension.mutableSharedFlowOfInput
import io.bifri.interview.cmp.padel.core.coroutines.extension.stateInViewModel
import io.bifri.interview.cmp.padel.core.exception.ExceptionHandler
import io.bifri.interview.cmp.padel.core.ui.Event
import io.bifri.interview.cmp.padel.core.ui.base.BaseViewModel
import io.bifri.interview.cmp.padel.feature.match.domain.usecase.GetMatchUseCase
import io.bifri.interview.cmp.padel.feature.match.ui.match.MatchNavigationAction.ShowError
import io.bifri.interview.cmp.padel.feature.match.ui.match.MatchPartialState.DataLoaded
import io.bifri.interview.cmp.padel.feature.match.ui.match.MatchPartialState.DataLoading
import io.bifri.interview.cmp.padel.feature.match.ui.match.MatchPartialState.DataLoadingError
import io.bifri.interview.cmp.padel.feature.match.ui.match.MatchPartialState.Navigate
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

class MatchViewModel(
    private val matchId: Long,
    private val tournamentName: String,
    private val totalCourts: Int?,
    coroutineDispatcher: CoroutineDispatcher,
    exceptionHandler: ExceptionHandler,
    private val getMatchUseCase: GetMatchUseCase,
) : BaseViewModel(coroutineDispatcher, exceptionHandler) {

    private val refreshes = mutableSharedFlowOfInput<Unit>()
    private val state: Flow<MatchState> = _state
    internal val uiState = state.map { it.uiState }
    internal val events = state.mapNotNull { it.event }

    internal fun onRefresh() {
        refreshes.tryEmit(Unit)
    }

    private val _state: Flow<MatchState>
        get() = merge(
            dataStream(),
        )
            .catch {
                exceptionHandler.handleException(it)
                emit(Navigate(ShowError(it)))
            }
            .runningFold(MatchState(), ::viewStateReducer)
            .flowOn(coroutineDispatcher)
            .stateInViewModel(
                scope = viewModelScope,
                initialValue = MatchState(),
            )

    private fun viewStateReducer(
        prevState: MatchState,
        changes: MatchPartialState,
    ): MatchState = when (changes) {
        DataLoading -> prevState.copy(uiState = prevState.uiState.copy(isDataLoading = true))

        is DataLoadingError -> prevState.copy(
            uiState = prevState.uiState.copy(isDataLoading = false),
            event = Event(ShowError(changes.error)),
        )

        is DataLoaded -> prevState.copy(
            uiState = prevState.uiState.copy(
                match = changes.match,
                isDataLoading = false,
                tournamentName = tournamentName,
                totalCourts = totalCourts,
            ),
        )

        is Navigate -> prevState.copy(event = Event(changes.navigationAction))
    }

    private fun dataStream() = refreshes
        .onStart { emit(Unit) }
        .flatMapLatest {
            flow { emit(getMatchUseCase(matchId)) }
                .map(::DataLoaded)
                .onStart<MatchPartialState> { emit(DataLoading) }
                .catch {
                    exceptionHandler.handleException(it)
                    emit(DataLoadingError(it))
                }
        }
}
