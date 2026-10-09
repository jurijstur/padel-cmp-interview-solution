package io.bifri.interview.cmp.padel.feature.match.ui.matches

import androidx.lifecycle.viewModelScope
import com.hoc081098.flowext.throttleTime
import io.bifri.interview.cmp.padel.core.coroutines.extension.mutableSharedFlowOfInput
import io.bifri.interview.cmp.padel.core.coroutines.extension.stateInViewModel
import io.bifri.interview.cmp.padel.core.exception.ExceptionHandler
import io.bifri.interview.cmp.padel.core.ui.Event
import io.bifri.interview.cmp.padel.core.ui.base.BaseViewModel
import io.bifri.interview.cmp.padel.core.ui.input.InputThrottle.DefaultSkipDuration
import io.bifri.interview.cmp.padel.feature.match.domain.usecase.GetTournamentMatchesUseCase
import io.bifri.interview.cmp.padel.feature.match.ui.matches.MatchesNavigationAction.OpenMatch
import io.bifri.interview.cmp.padel.feature.match.ui.matches.MatchesNavigationAction.ShowError
import io.bifri.interview.cmp.padel.feature.match.ui.matches.MatchesPartialState.Loading
import io.bifri.interview.cmp.padel.feature.match.ui.matches.MatchesPartialState.LoadingFailed
import io.bifri.interview.cmp.padel.feature.match.ui.matches.MatchesPartialState.MatchesLoaded
import io.bifri.interview.cmp.padel.feature.match.ui.matches.MatchesPartialState.Navigate
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

class MatchesViewModel(
    private val tournamentId: Long,
    private val tournamentName: String,
    coroutineDispatcher: CoroutineDispatcher,
    exceptionHandler: ExceptionHandler,
    private val getTournamentMatchesUseCase: GetTournamentMatchesUseCase,
) : BaseViewModel(coroutineDispatcher, exceptionHandler) {

    private val refreshClicks = mutableSharedFlowOfInput<Unit>()
    private val matchClicks = mutableSharedFlowOfInput<Pair<Long, Int?>>()
    private val state: Flow<MatchesState> = _state
    internal val uiState = state.map { it.uiState }
    internal val events = state.mapNotNull { it.event }

    private val _state: Flow<MatchesState>
        get() = merge(
            refreshClicksStream(),
            matchClicksStream(),
        )
            .catch {
                exceptionHandler.handleException(it)
                emit(Navigate(ShowError(it)))
            }
            .runningFold(MatchesState(), ::viewStateReducer)
            .flowOn(coroutineDispatcher)
            .stateInViewModel(
                scope = viewModelScope,
                initialValue = MatchesState(),
            )

    internal fun onRefresh() {
        refreshClicks.tryEmit(Unit)
    }

    internal fun onMatchClick(matchId: Long, totalCourts: Int?) {
        matchClicks.tryEmit(matchId to totalCourts)
    }

    private fun matchesStream(): Flow<MatchesPartialState> = flow {
        emit(Loading)
        emit(MatchesLoaded(getTournamentMatchesUseCase(tournamentId)))
    }
        .catch { emit(LoadingFailed(it)) }

    private fun refreshClicksStream(): Flow<MatchesPartialState> = refreshClicks
        .onStart { emit(Unit) }
        .flatMapLatest { matchesStream() }

    private fun matchClicksStream(): Flow<MatchesPartialState> = matchClicks
        .throttleTime(DefaultSkipDuration)
        .map { (matchId, totalCourts) ->
            Navigate(
                OpenMatch(
                    matchId = matchId,
                    totalCourts = totalCourts,
                ),
            )
        }

    private fun viewStateReducer(
        prevState: MatchesState,
        changes: MatchesPartialState,
    ): MatchesState = when (changes) {
        Loading -> prevState.copy(
            uiState = prevState.uiState.copy(isDataLoading = true),
            event = null,
        )

        is MatchesLoaded -> prevState.copy(
            uiState = prevState.uiState.copy(
                items = changes.matches,
                isDataLoading = false,
                tournamentName = tournamentName,
            ),
            event = null,
        )

        is LoadingFailed -> prevState.copy(
            uiState = prevState.uiState.copy(isDataLoading = false),
            event = Event(ShowError(changes.error)),
        )

        is Navigate -> prevState.copy(event = Event(changes.navigationAction))
    }
}
