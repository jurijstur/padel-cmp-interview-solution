package io.bifri.interview.cmp.padel.feature.tournament.data.datasource.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TournamentsResponseDto(
    @SerialName("data") val data: List<TournamentDto?>? = null,
) {
    @Serializable
    data class TournamentDto(
        @SerialName("id") val id: Long? = null,
        @SerialName("name") val name: String? = null,
        @SerialName("location") val location: String? = null,
        @SerialName("country") val country: String? = null,
        @SerialName("level") val level: String? = null,
        @SerialName("status") val status: String? = null,
        @SerialName("photo_url") val photoUrl: String? = null,
        @SerialName("start_date") val startDate: String? = null,
        @SerialName("end_date") val endDate: String? = null,
    )
}
