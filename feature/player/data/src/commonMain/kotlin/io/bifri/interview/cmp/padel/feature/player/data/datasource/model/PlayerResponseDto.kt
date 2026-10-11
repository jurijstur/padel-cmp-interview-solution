package io.bifri.interview.cmp.padel.feature.player.data.datasource.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerResponseDto(
    @SerialName("id") val id: Long? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("short_name") val shortName: String? = null,
    @SerialName("photo_url") val photoUrl: String? = null,
    @SerialName("category") val category: String? = null,
    @SerialName("nationality") val nationality: String? = null,
    @SerialName("ranking") val ranking: String? = null,
    @SerialName("points") val points: Int? = null,
    @SerialName("elo") val elo: Int? = null,
    @SerialName("height") val height: Int? = null,
    @SerialName("side") val side: String? = null,
    @SerialName("hand") val hand: String? = null,
    @SerialName("birthplace") val birthplace: String? = null,
    @SerialName("age") val age: String? = null,
)
