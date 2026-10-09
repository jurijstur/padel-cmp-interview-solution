package io.bifri.interview.cmp.padel.feature.match.data.repository

import io.bifri.interview.cmp.padel.core.exception.ExceptionHandler
import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.MatchWithPhotosDto
import io.bifri.interview.cmp.padel.feature.match.data.datasource.remote.MatchRemoteDataSource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

internal class PadelApiMatchRepository(
    private val exceptionHandler: ExceptionHandler,
    private val remoteDataSource: MatchRemoteDataSource,
) : MatchRepository {

    override suspend fun getTournamentMatches(tournamentId: Long) = remoteDataSource.getTournamentMatches(tournamentId)

    override suspend fun getMatch(matchId: Long): MatchWithPhotosDto = coroutineScope {
        val matchResponseDto = remoteDataSource.getMatch(matchId)
        val teamOnePath = matchResponseDto.players?.teamOne?.firstOrNull()?.connections?.pair
        val teamTwoPath = matchResponseDto.players?.teamTwo?.firstOrNull()?.connections?.pair
        val teamOnePlayerPhotoUrlsDeferred = getTeamPlayerPhotoUrls(teamOnePath)
        val teamTwoPlayerPhotoUrlsDeferred = getTeamPlayerPhotoUrls(teamTwoPath)

        MatchWithPhotosDto(
            match = matchResponseDto,
            teamOnePlayerPhotoUrls = teamOnePlayerPhotoUrlsDeferred.await(),
            teamTwoPlayerPhotoUrls = teamTwoPlayerPhotoUrlsDeferred.await(),
        )
    }

    @Suppress("TooGenericExceptionCaught")
    private fun CoroutineScope.getTeamPlayerPhotoUrls(teamPhotoPath: String?): Deferred<List<String?>> = async {
        teamPhotoPath ?: return@async emptyList()

        try {
            remoteDataSource.getPair(teamPhotoPath).players?.map { it?.photoUrl }.orEmpty()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            exceptionHandler.handleException(e)
            emptyList()
        }
    }
}
