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
    )

    private fun MatchResponseDto.toMatch(
        teamOnePlayerPhotoUrls: List<String?>,
        teamTwoPlayerPhotoUrls: List<String?>,
    ) = Match(
        id = requireNotNull(id),
        courtName = court,
        roundName = roundName,
        teamOne = players?.teamOne.toTeam(teamOnePlayerPhotoUrls),
        teamTwo = players?.teamTwo.toTeam(teamTwoPlayerPhotoUrls),
        scores = score?.mapIndexed { index, score -> score.toSetScore(index + 1) }.orEmpty(),
        winnerTeam = winner.toTeamIndex(),
    )

    private fun List<PlayerDto?>?.toTeam(playerPhotoUrls: List<String?>) = this
        ?.withIndex()
        ?.mapNotNull { (index, playerDto) ->
            playerDto?.let {
                Player(
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
