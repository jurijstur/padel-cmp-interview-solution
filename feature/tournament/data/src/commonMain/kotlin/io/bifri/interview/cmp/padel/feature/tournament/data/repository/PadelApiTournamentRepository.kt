package io.bifri.interview.cmp.padel.feature.tournament.data.repository

import io.bifri.interview.cmp.padel.feature.tournament.data.datasource.remote.TournamentRemoteDataSource

internal class PadelApiTournamentRepository(
    private val remoteDataSource: TournamentRemoteDataSource,
) : TournamentRepository {
    override suspend fun getTournaments() = remoteDataSource.getTournaments()
    override suspend fun getTournament(id: Long) = remoteDataSource.getTournament(id)
}
