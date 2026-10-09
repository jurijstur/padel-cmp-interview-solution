package io.bifri.interview.cmp.padel.feature.tournament.data.datasource.remote

import io.bifri.interview.cmp.padel.feature.tournament.data.datasource.model.TournamentResponseDto
import io.bifri.interview.cmp.padel.feature.tournament.data.datasource.model.TournamentsResponseDto

internal interface TournamentRemoteDataSource {
    suspend fun getTournaments(): TournamentsResponseDto
    suspend fun getTournament(id: Long): TournamentResponseDto
}
