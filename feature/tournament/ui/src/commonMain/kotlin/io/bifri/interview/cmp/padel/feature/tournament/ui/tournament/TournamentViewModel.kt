package io.bifri.interview.cmp.padel.feature.tournament.ui.tournament

import androidx.lifecycle.viewModelScope
import com.hoc081098.flowext.throttleTime
import io.bifri.interview.cmp.padel.core.coroutines.extension.mutableSharedFlowOfInput
import io.bifri.interview.cmp.padel.core.coroutines.extension.stateInViewModel
import io.bifri.interview.cmp.padel.core.exception.ExceptionHandler
import io.bifri.interview.cmp.padel.core.ui.Event
import io.bifri.interview.cmp.padel.core.ui.base.BaseViewModel
import io.bifri.interview.cmp.padel.core.ui.input.InputThrottle.DefaultSkipDuration
import io.bifri.interview.cmp.padel.feature.tournament.domain.usecase.GetTournamentUseCase
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournament.TournamentNavigationAction.OpenMatches
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournament.TournamentNavigationAction.ShowError
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournament.TournamentPartialState.DataLoaded
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournament.TournamentPartialState.DataLoading
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournament.TournamentPartialState.DataLoadingError
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournament.TournamentPartialState.Navigate
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

class TournamentViewModel(
    private val tournamentId: Long,
    coroutineDispatcher: CoroutineDispatcher,
    exceptionHandler: ExceptionHandler,
    private val getTournamentUseCase: GetTournamentUseCase,
) : BaseViewModel(coroutineDispatcher, exceptionHandler) {
    private val refreshes = mutableSharedFlowOfInput<Unit>()
    private val matchesClicks = mutableSharedFlowOfInput<String>()
    private val state: Flow<TournamentState> = _state
    internal val uiState = state.map { it.uiState }
    internal val events = state.mapNotNull { it.event }

    internal fun onRefresh() {
        refreshes.tryEmit(Unit)
    }

    internal fun onMatchesClick(tournamentName: String) {
        matchesClicks.tryEmit(tournamentName)
    }

    private val _state: Flow<TournamentState>
        get() = merge(
            dataStream(),
            matchesClicksStream(),
        )
            .catch {
                exceptionHandler.handleException(it)
                emit(Navigate(ShowError(it)))
            }
            .runningFold(TournamentState(), ::viewStateReducer)
            .flowOn(coroutineDispatcher)
            .stateInViewModel(
                scope = viewModelScope,
                initialValue = TournamentState(),
            )

    private fun viewStateReducer(
        prevState: TournamentState,
        changes: TournamentPartialState,
    ): TournamentState = when (changes) {
        DataLoading -> prevState.copy(uiState = prevState.uiState.copy(isDataLoading = true))

        is DataLoadingError -> prevState.copy(
            uiState = prevState.uiState.copy(isDataLoading = false),
            event = Event(ShowError(changes.error)),
        )

        is DataLoaded -> prevState.copy(
            uiState = prevState.uiState.copy(
                tournament = changes.tournament,
                isDataLoading = false,
            ),
        )

        is Navigate -> prevState.copy(event = Event(changes.navigationAction))
    }

    private fun dataStream() = refreshes
        .onStart { emit(Unit) }
        .flatMapLatest {
            flow { emit(getTournamentUseCase(tournamentId)) }
                .map(::DataLoaded)
                .onStart<TournamentPartialState> { emit(DataLoading) }
                .catch {
                    exceptionHandler.handleException(it)
                    emit(DataLoadingError(it))
                }
        }

    private fun matchesClicksStream(): Flow<TournamentPartialState> = matchesClicks
        .throttleTime(DefaultSkipDuration)
        .map { Navigate(OpenMatches(it)) }
}
