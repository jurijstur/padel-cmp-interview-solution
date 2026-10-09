package io.bifri.interview.cmp.padel.feature.match.domain.mapper

import io.bifri.interview.cmp.padel.core.exception.ExceptionHandler
import io.bifri.interview.cmp.padel.core.extensions.datetime.DateTimeUtils
import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.MatchesResponseDto
import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.MatchesResponseDto.MatchDto
import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.MatchesResponseDto.MatchDto.PlayersDto.PlayerDto
import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.MatchesResponseDto.MatchDto.ScoreDto
import io.bifri.interview.cmp.padel.feature.match.domain.model.TournamentMatch
import io.bifri.interview.cmp.padel.feature.match.domain.model.TournamentMatch.SetScore
import io.bifri.interview.cmp.padel.feature.match.domain.model.TournamentMatch.Team
import io.bifri.interview.cmp.padel.feature.match.domain.model.TournamentMatch.TeamIndex.TeamOne
import io.bifri.interview.cmp.padel.feature.match.domain.model.TournamentMatch.TeamIndex.TeamTwo
import kotlinx.datetime.LocalDate

class MatchesDomainMapper(
    private val exceptionHandler: ExceptionHandler,
    private val dateTimeUtils: DateTimeUtils,
) {

    fun MatchesResponseDto.toDomain(): List<TournamentMatch> = data
        ?.mapNotNull {
            try {
                it?.toDomain()
            } catch (e: IllegalArgumentException) {
                exceptionHandler.handleException(e)
                null
            }
        }
        .orEmpty()

    private fun MatchDto.toDomain() = TournamentMatch(
        id = requireNotNull(id),
        courtName = court,
        courtOrder = courtOrder,
        status = status,
        roundName = roundName,
        teamOne = players?.teamOne.toTeam(),
        teamTwo = players?.teamTwo.toTeam(),
        scores = score?.mapIndexed { index, score -> score.toSetScore(index + 1) }.orEmpty(),
        winnerTeam = winner?.toTeamIndex(),
        playedAt = playedAt?.toDateOrNull(),
        scheduledAt = scheduledAt?.toDateOrNull(),
    )

    private fun List<PlayerDto?>?.toTeam() = Team(
        name = this?.mapNotNull { it?.name }?.joinToString(" / ").orEmpty(),
    )

    private fun String?.toTeamIndex() = when (this) {
        "team_1" -> TeamOne
        "team_2" -> TeamTwo
        else -> null
    }

    private fun ScoreDto?.toSetScore(setNumber: Int) = SetScore(
        setNumber = setNumber,
        teamOneGames = this?.teamOne,
        teamTwoGames = this?.teamTwo,
    )

    private fun String.toDateOrNull(): LocalDate? = with(dateTimeUtils) { isoDate() }
}
