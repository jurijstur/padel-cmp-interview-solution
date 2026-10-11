package io.bifri.interview.cmp.padel.feature.player.data.datasource.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayersResponseDto(
    @SerialName("data") val data: List<PlayerDto?>? = null,
) {
    @Serializable
    data class PlayerDto(
        @SerialName("id") val id: Long? = null,
        @SerialName("name") val name: String? = null,
        @SerialName("short_name") val shortName: String? = null,
        @SerialName("photo_url") val photoUrl: String? = null,
        @SerialName("nationality") val nationality: String? = null,
        @SerialName("ranking") val ranking: String? = null,
        @SerialName("points") val points: Int? = null,
        @SerialName("category") val category: String? = null,
    )
}
