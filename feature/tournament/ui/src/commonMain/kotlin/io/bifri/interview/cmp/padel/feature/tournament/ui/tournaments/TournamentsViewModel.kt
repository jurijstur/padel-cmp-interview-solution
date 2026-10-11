package io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments

import androidx.lifecycle.viewModelScope
import com.hoc081098.flowext.throttleTime
import io.bifri.interview.cmp.padel.core.coroutines.extension.mutableSharedFlowOfInput
import io.bifri.interview.cmp.padel.core.coroutines.extension.stateInViewModel
import io.bifri.interview.cmp.padel.core.exception.ExceptionHandler
import io.bifri.interview.cmp.padel.core.location.DeviceLocation
import io.bifri.interview.cmp.padel.core.location.LocationService
import io.bifri.interview.cmp.padel.core.ui.Event
import io.bifri.interview.cmp.padel.core.ui.base.BaseViewModel
import io.bifri.interview.cmp.padel.core.ui.input.InputThrottle.DefaultSkipDuration
import io.bifri.interview.cmp.padel.feature.tournament.domain.model.TournamentsTournament
import io.bifri.interview.cmp.padel.feature.tournament.domain.usecase.GetTournamentsUseCase
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments.TournamentsNavigationAction.OpenPlayers
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments.TournamentsNavigationAction.OpenTournament
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments.TournamentsNavigationAction.ShowError
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments.TournamentsPartialState.DataLoaded
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments.TournamentsPartialState.DataLoading
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments.TournamentsPartialState.DataLoadingError
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments.TournamentsPartialState.Navigate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
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
    private val locationService: LocationService,
) : BaseViewModel(coroutineDispatcher, exceptionHandler) {
    private val refreshes = mutableSharedFlowOfInput<Unit>()
    private val tournamentClicks = mutableSharedFlowOfInput<Long>()
    private val playersClicks = mutableSharedFlowOfInput<Unit>()
    private val locationPermission = MutableStateFlow<Boolean?>(null)
    private val state: Flow<TournamentsState> = _state
    internal val uiState = state.map { it.uiState }
    internal val events = state.mapNotNull { it.event }

    internal fun onRefresh() {
        refreshes.tryEmit(Unit)
    }

    internal fun onTournamentClick(id: Long) {
        tournamentClicks.tryEmit(id)
    }

    internal fun onPlayersClick() {
        playersClicks.tryEmit(Unit)
    }

    internal fun onLocationPermission(granted: Boolean) {
        locationPermission.value = granted
    }

    private val _state: Flow<TournamentsState>
        get() = merge(
            dataStream(),
            tournamentClicksStream(),
            playersClicksStream(),
        )
            .catch {
                exceptionHandler.handleException(it)
                emit(Navigate(ShowError(it)))
            }
            .runningFold(TournamentsState(), ::reduceTournamentsState)
            .flowOn(coroutineDispatcher)
            .stateInViewModel(
                scope = viewModelScope,
                initialValue = TournamentsState(),
            )

    private fun dataStream(): Flow<TournamentsPartialState> = sortedTournamentLoads(
        loads = tournaments(),
        locations = locations(),
    )

    private fun tournaments(): Flow<TournamentLoad> = refreshes
        .onStart { emit(Unit) }
        .flatMapLatest {
            flow {
                emit(TournamentLoad.Loading)
                emit(TournamentLoad.Ready(getTournamentsUseCase()))
            }.catch {
                exceptionHandler.handleException(it)
                emit(TournamentLoad.Failed(it))
            }
        }

    private fun locations(): Flow<DeviceLocation> = locationReadings(
        permission = locationPermission,
        service = locationService,
    )

    private fun tournamentClicksStream(): Flow<TournamentsPartialState> = tournamentClicks
        .throttleTime(DefaultSkipDuration)
        .map { Navigate(OpenTournament(it)) }

    private fun playersClicksStream(): Flow<TournamentsPartialState> = playersClicks
        .throttleTime(DefaultSkipDuration)
        .map { Navigate(OpenPlayers) }
}

internal fun reduceTournamentsState(
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
            isLocationUnavailable = changes.isLocationUnavailable,
        ),
    )

    is Navigate -> prevState.copy(event = Event(changes.navigationAction))
}
