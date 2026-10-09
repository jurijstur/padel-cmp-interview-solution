package io.bifri.interview.cmp.padel.feature.tournament.data.repository

import io.bifri.interview.cmp.padel.feature.tournament.data.datasource.demo.DemoTournamentDataSource
import io.bifri.interview.cmp.padel.feature.tournament.data.datasource.demo.DemoTournamentsDataSource

internal class DemoTournamentRepository(
    private val tournamentsDataSource: DemoTournamentsDataSource,
    private val tournamentDataSource: DemoTournamentDataSource,
) : TournamentRepository {
    override suspend fun getTournaments() = tournamentsDataSource.getTournaments()
    override suspend fun getTournament(id: Long) = tournamentDataSource.getTournament(id)
}
