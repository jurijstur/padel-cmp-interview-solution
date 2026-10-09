package io.bifri.interview.cmp.padel.feature.match.data.repository

import io.bifri.interview.cmp.padel.feature.match.data.datasource.demo.DemoMatchDataSource
import io.bifri.interview.cmp.padel.feature.match.data.datasource.demo.DemoMatchesDataSource
import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.MatchWithPhotosDto

internal class DemoMatchRepository(
    private val demoMatchDataSource: DemoMatchDataSource,
    private val demoMatchesDataSource: DemoMatchesDataSource,
) : MatchRepository {
    override suspend fun getTournamentMatches(tournamentId: Long) = demoMatchesDataSource.getMatches()

    override suspend fun getMatch(matchId: Long): MatchWithPhotosDto = MatchWithPhotosDto(
        match = demoMatchDataSource.getMatch(matchId),
        teamOnePlayerPhotoUrls = emptyList(),
        teamTwoPlayerPhotoUrls = emptyList(),
    )
}
