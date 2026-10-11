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
        val teamOnePlayersDeferred = getTeamPlayers(teamOnePath)
        val teamTwoPlayersDeferred = getTeamPlayers(teamTwoPath)
        val teamOnePlayers = teamOnePlayersDeferred.await()
        val teamTwoPlayers = teamTwoPlayersDeferred.await()

        MatchWithPhotosDto(
            match = matchResponseDto,
            teamOnePlayerPhotoUrls = teamOnePlayers.photoUrls,
            teamTwoPlayerPhotoUrls = teamTwoPlayers.photoUrls,
            teamOnePlayerIds = teamOnePlayers.ids,
            teamTwoPlayerIds = teamTwoPlayers.ids,
        )
    }

    @Suppress("TooGenericExceptionCaught")
    private fun CoroutineScope.getTeamPlayers(teamPhotoPath: String?): Deferred<TeamPlayers> = async {
        teamPhotoPath ?: return@async TeamPlayers(ids = emptyList(), photoUrls = emptyList())

        try {
            val players = remoteDataSource.getPair(teamPhotoPath).players.orEmpty()
            TeamPlayers(
                ids = players.map { it?.id },
                photoUrls = players.map { it?.photoUrl },
            )
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            exceptionHandler.handleException(e)
            TeamPlayers(ids = emptyList(), photoUrls = emptyList())
        }
    }
}

private data class TeamPlayers(
    val ids: List<Long?>,
    val photoUrls: List<String?>,
)
