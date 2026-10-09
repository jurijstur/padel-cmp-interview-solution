package io.bifri.interview.cmp.padel.feature.tournament.data.repository

import io.bifri.interview.cmp.padel.feature.tournament.data.datasource.model.TournamentResponseDto
import io.bifri.interview.cmp.padel.feature.tournament.data.datasource.model.TournamentsResponseDto

interface TournamentRepository {
    suspend fun getTournaments(): TournamentsResponseDto
    suspend fun getTournament(id: Long): TournamentResponseDto
}
