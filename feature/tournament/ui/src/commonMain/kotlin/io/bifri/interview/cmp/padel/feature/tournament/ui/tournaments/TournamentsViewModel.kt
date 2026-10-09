package io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments

import androidx.lifecycle.viewModelScope
import com.hoc081098.flowext.throttleTime
import io.bifri.interview.cmp.padel.core.coroutines.extension.mutableSharedFlowOfInput
import io.bifri.interview.cmp.padel.core.coroutines.extension.stateInViewModel
import io.bifri.interview.cmp.padel.core.exception.ExceptionHandler
import io.bifri.interview.cmp.padel.core.ui.Event
import io.bifri.interview.cmp.padel.core.ui.base.BaseViewModel
import io.bifri.interview.cmp.padel.core.ui.input.InputThrottle.DefaultSkipDuration
import io.bifri.interview.cmp.padel.feature.tournament.domain.usecase.GetTournamentsUseCase
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments.TournamentsNavigationAction.OpenTournament
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments.TournamentsNavigationAction.ShowError
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments.TournamentsPartialState.DataLoaded
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments.TournamentsPartialState.DataLoading
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments.TournamentsPartialState.DataLoadingError
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments.TournamentsPartialState.Navigate
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

class TournamentsViewModel(
    coroutineDispatcher: CoroutineDispatcher,
    exceptionHandler: ExceptionHandler,
    private val getTournamentsUseCase: GetTournamentsUseCase,
) : BaseViewModel(coroutineDispatcher, exceptionHandler) {
    private val refreshes = mutableSharedFlowOfInput<Unit>()
    private val tournamentClicks = mutableSharedFlowOfInput<Long>()
    private val state: Flow<TournamentsState> = _state
    internal val uiState = state.map { it.uiState }
    internal val events = state.mapNotNull { it.event }

    internal fun onRefresh() {
        refreshes.tryEmit(Unit)
    }

    internal fun onTournamentClick(id: Long) {
        tournamentClicks.tryEmit(id)
    }

    private val _state: Flow<TournamentsState>
        get() = merge(
            dataStream(),
            tournamentClicksStream(),
        )
            .catch {
                exceptionHandler.handleException(it)
                emit(Navigate(ShowError(it)))
            }
            .runningFold(TournamentsState(), ::viewStateReducer)
            .flowOn(coroutineDispatcher)
            .stateInViewModel(
                scope = viewModelScope,
                initialValue = TournamentsState(),
            )

    private fun viewStateReducer(
        prevState: TournamentsState,
        changes: TournamentsPartialState,
    ): TournamentsState = when (changes) {
        DataLoading -> prevState.copy(uiState = prevState.uiState.copy(isDataLoading = true))

        is DataLoadingError -> prevState.copy(
            uiState = prevState.uiState.copy(isDataLoading = false),
            event = Event(ShowError(changes.error)),
        )

        is DataLoaded -> prevState.copy(
            uiState = prevState.uiState.copy(
                items = changes.items,
                isDataLoading = false,
            ),
        )

        is Navigate -> prevState.copy(event = Event(changes.navigationAction))
    }

    private fun dataStream() = refreshes
        .onStart { emit(Unit) }
        .flatMapLatest {
            flow { emit(getTournamentsUseCase()) }
                .map(::DataLoaded)
                .onStart<TournamentsPartialState> { emit(DataLoading) }
                .catch {
                    exceptionHandler.handleException(it)
                    emit(DataLoadingError(it))
                }
        }

    private fun tournamentClicksStream(): Flow<TournamentsPartialState> = tournamentClicks
        .throttleTime(DefaultSkipDuration)
        .map { Navigate(OpenTournament(it)) }
}
