package io.bifri.interview.cmp.padel.feature.match.data.datasource.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PairDto(
    @SerialName("players") val players: List<PairPlayerDto?>? = null,
) {
    @Serializable
    data class PairPlayerDto(
        @SerialName("photo_url") val photoUrl: String? = null,
    )
}
