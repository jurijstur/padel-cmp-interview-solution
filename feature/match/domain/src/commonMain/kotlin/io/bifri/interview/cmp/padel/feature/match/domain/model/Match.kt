package io.bifri.interview.cmp.padel.feature.match.domain.model

data class Match(
    val id: Long,
    val courtName: String?,
    val roundName: String?,
    val teamOne: Team?,
    val teamTwo: Team?,
    val scores: List<SetScore>,
    val winnerTeam: TeamIndex?,
) {
    data class Team(
        val players: List<Player>,
    ) {
        data class Player(
            val name: String?,
            val playerPhotoUrl: String?,
        )
    }

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
