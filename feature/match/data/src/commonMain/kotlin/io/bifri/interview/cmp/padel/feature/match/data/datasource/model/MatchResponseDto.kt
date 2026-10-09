package io.bifri.interview.cmp.padel.feature.match.data.datasource.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchResponseDto(
    @SerialName("court") val court: String? = null,
    @SerialName("id") val id: Long?,
    @SerialName("players") val players: PlayersDto? = null,
    @SerialName("round_name") val roundName: String? = null,
    @SerialName("score") val score: List<ScoreDto>? = null,
    @SerialName("winner") val winner: String? = null,
) {
    @Serializable
    data class PlayersDto(
        @SerialName("team_1") val teamOne: List<PlayerDto?>? = null,
        @SerialName("team_2") val teamTwo: List<PlayerDto?>? = null,
    ) {
        @Serializable
        data class PlayerDto(
            @SerialName("connections") val connections: PlayerConnectionsDto? = null,
            @SerialName("name") val name: String? = null,
        ) {
            @Serializable
            data class PlayerConnectionsDto(
                @SerialName("pair") val pair: String? = null,
            )
        }
    }

    @Serializable
    data class ScoreDto(
        @SerialName("team_1") val teamOne: String? = null,
        @SerialName("team_2") val teamTwo: String? = null,
    )
}
