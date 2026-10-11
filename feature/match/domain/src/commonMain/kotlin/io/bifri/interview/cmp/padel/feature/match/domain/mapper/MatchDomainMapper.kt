package io.bifri.interview.cmp.padel.feature.match.domain.mapper

import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.MatchResponseDto
import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.MatchResponseDto.PlayersDto.PlayerDto
import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.MatchResponseDto.ScoreDto
import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.MatchWithPhotosDto
import io.bifri.interview.cmp.padel.feature.match.domain.model.Match
import io.bifri.interview.cmp.padel.feature.match.domain.model.Match.SetScore
import io.bifri.interview.cmp.padel.feature.match.domain.model.Match.Team
import io.bifri.interview.cmp.padel.feature.match.domain.model.Match.Team.Player
import io.bifri.interview.cmp.padel.feature.match.domain.model.Match.TeamIndex

class MatchDomainMapper {

    fun MatchWithPhotosDto.toDomain() = match.toMatch(
        teamOnePlayerPhotoUrls = teamOnePlayerPhotoUrls,
        teamTwoPlayerPhotoUrls = teamTwoPlayerPhotoUrls,
        teamOnePlayerIds = teamOnePlayerIds,
        teamTwoPlayerIds = teamTwoPlayerIds,
        usePairPathPlayerIds = usePairPathPlayerIds,
    )

    private fun MatchResponseDto.toMatch(
        teamOnePlayerPhotoUrls: List<String?>,
        teamTwoPlayerPhotoUrls: List<String?>,
        teamOnePlayerIds: List<Long?>,
        teamTwoPlayerIds: List<Long?>,
        usePairPathPlayerIds: Boolean,
    ) = Match(
        id = requireNotNull(id),
        courtName = court,
        roundName = roundName,
        teamOne = players?.teamOne.toTeam(teamOnePlayerPhotoUrls, teamOnePlayerIds, usePairPathPlayerIds),
        teamTwo = players?.teamTwo.toTeam(teamTwoPlayerPhotoUrls, teamTwoPlayerIds, usePairPathPlayerIds),
        scores = score?.mapIndexed { index, score -> score.toSetScore(index + 1) }.orEmpty(),
        winnerTeam = winner.toTeamIndex(),
    )

    private fun List<PlayerDto?>?.toTeam(
        playerPhotoUrls: List<String?>,
        playerIds: List<Long?>,
        usePairPathPlayerIds: Boolean,
    ) = this
        ?.withIndex()
        ?.mapNotNull { (index, playerDto) ->
            playerDto?.let {
                val pairPathId = if (usePairPathPlayerIds) {
                    playerIdFromPairPath(playerDto.connections?.pair, index)
                } else {
                    null
                }
                Player(
                    id = playerIds.getOrNull(index) ?: pairPathId,
                    name = playerDto.name,
                    playerPhotoUrl = playerPhotoUrls.getOrNull(index),
                )
            }
        }
        ?.let { Team(it) }

    private fun ScoreDto.toSetScore(setNumber: Int) = SetScore(
        setNumber = setNumber,
        teamOneGames = teamOne,
        teamTwoGames = teamTwo,
    )

    private fun String?.toTeamIndex() = when (this) {
        "team_1" -> TeamIndex.TeamOne
        "team_2" -> TeamIndex.TeamTwo
        else -> null
    }
}

internal fun playerIdFromPairPath(pairPath: String?, playerIndex: Int): Long? {
    if (playerIndex < 0) return null
    return pairPath
        ?.substringAfterLast('/')
        ?.split('-')
        ?.getOrNull(playerIndex)
        ?.toLongOrNull()
}
