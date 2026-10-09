package io.bifri.interview.cmp.padel.feature.match.domain.model

import kotlinx.datetime.LocalDate

data class TournamentMatch(
    val id: Long,
    val courtName: String?,
    val courtOrder: Int?,
    val status: String?,
    val roundName: String?,
    val teamOne: Team?,
    val teamTwo: Team?,
    val scores: List<SetScore>,
    val winnerTeam: TeamIndex?,
    val playedAt: LocalDate?,
    val scheduledAt: LocalDate?,
) {
    data class Team(
        val name: String,
    )

    data class SetScore(
        val setNumber: Int,
        val teamOneGames: String?,
        val teamTwoGames: String?,
    )

    enum class TeamIndex {
        TeamOne,
        TeamTwo,
    }
}
