package io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments

import io.bifri.interview.cmp.padel.core.location.DeviceLocation
import io.bifri.interview.cmp.padel.core.location.LocationService
import io.bifri.interview.cmp.padel.feature.tournament.domain.location.sortTournamentsByDistance
import io.bifri.interview.cmp.padel.feature.tournament.domain.model.TournamentsTournament
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments.TournamentsPartialState.DataLoaded
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments.TournamentsPartialState.DataLoading
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments.TournamentsPartialState.DataLoadingError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart

internal fun locationReadings(
    permission: Flow<Boolean?>,
    service: LocationService,
): Flow<DeviceLocation> = permission
    .flatMapLatest { granted ->
        when (granted) {
            null -> flowOf(DeviceLocation.Pending)
            false -> flowOf(DeviceLocation.Denied)
            true -> service.observe()
        }
    }
    .onStart { emit(DeviceLocation.Pending) }

internal fun sortedTournamentLoads(
    loads: Flow<TournamentLoad>,
    locations: Flow<DeviceLocation>,
): Flow<TournamentsPartialState> = combine(loads, locations) { load, location ->
    when (load) {
        TournamentLoad.Loading -> DataLoading

        is TournamentLoad.Failed -> DataLoadingError(load.error)

        is TournamentLoad.Ready -> {
            val known = location as? DeviceLocation.Known
            DataLoaded(
                items = sortTournamentsByDistance(
                    tournaments = load.tournaments,
                    latitude = known?.latitude,
                    longitude = known?.longitude,
                ),
                isLocationUnavailable = location is DeviceLocation.Denied ||
                    location is DeviceLocation.Unavailable,
            )
        }
    }
}.distinctUntilChanged()

internal sealed interface TournamentLoad {
    data object Loading : TournamentLoad

    data class Ready(val tournaments: List<TournamentsTournament>) : TournamentLoad

    data class Failed(val error: Throwable) : TournamentLoad
}
