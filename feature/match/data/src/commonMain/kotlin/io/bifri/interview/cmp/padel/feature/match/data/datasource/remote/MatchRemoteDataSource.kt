package io.bifri.interview.cmp.padel.feature.match.data.datasource.remote

import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.MatchResponseDto
import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.MatchesResponseDto
import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.PairDto

internal interface MatchRemoteDataSource {
    suspend fun getTournamentMatches(tournamentId: Long): MatchesResponseDto
    suspend fun getMatch(id: Long): MatchResponseDto
    suspend fun getPair(path: String): PairDto
}
