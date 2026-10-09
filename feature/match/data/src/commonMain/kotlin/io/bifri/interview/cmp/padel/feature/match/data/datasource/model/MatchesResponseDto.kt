package io.bifri.interview.cmp.padel.feature.match.data.datasource.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchesResponseDto(
    @SerialName("data") val data: List<MatchDto?>? = null,
) {
    @Serializable
    data class MatchDto(
        @SerialName("court") val court: String? = null,
        @SerialName("court_order") val courtOrder: Int? = null,
        @SerialName("id") val id: Long?,
        @SerialName("played_at") val playedAt: String? = null,
        @SerialName("players") val players: PlayersDto? = null,
        @SerialName("round_name") val roundName: String? = null,
        @SerialName("scheduled_at") val scheduledAt: String? = null,
        @SerialName("score") val score: List<ScoreDto?>? = null,
        @SerialName("status") val status: String? = null,
        @SerialName("winner") val winner: String? = null,
    ) {
        @Serializable
        data class PlayersDto(
            @SerialName("team_1") val teamOne: List<PlayerDto?>? = null,
            @SerialName("team_2") val teamTwo: List<PlayerDto?>? = null,
        ) {
            @Serializable
            data class PlayerDto(
                @SerialName("name") val name: String? = null,
            )
        }

        @Serializable
        data class ScoreDto(
            @SerialName("team_1") val teamOne: String? = null,
            @SerialName("team_2") val teamTwo: String? = null,
        )
    }
}
