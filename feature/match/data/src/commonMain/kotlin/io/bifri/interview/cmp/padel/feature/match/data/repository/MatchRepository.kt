package io.bifri.interview.cmp.padel.feature.match.data.repository

import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.MatchWithPhotosDto
import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.MatchesResponseDto

interface MatchRepository {
    suspend fun getTournamentMatches(tournamentId: Long): MatchesResponseDto
    suspend fun getMatch(matchId: Long): MatchWithPhotosDto
}
