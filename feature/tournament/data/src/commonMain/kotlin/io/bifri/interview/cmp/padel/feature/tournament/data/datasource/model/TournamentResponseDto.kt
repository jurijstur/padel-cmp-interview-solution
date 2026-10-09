package io.bifri.interview.cmp.padel.feature.tournament.data.datasource.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TournamentResponseDto(
    @SerialName("id") val id: Long? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("location") val location: String? = null,
    @SerialName("country") val country: String? = null,
    @SerialName("timezone") val timezone: String? = null,
    @SerialName("level") val level: String? = null,
    @SerialName("status") val status: String? = null,
    @SerialName("venue") val venue: VenueDto? = null,
    @SerialName("prize") val prize: PrizeDto? = null,
    @SerialName("photo_url") val photoUrl: String? = null,
    @SerialName("court_type") val courtType: String? = null,
    @SerialName("start_date") val startDate: String? = null,
    @SerialName("end_date") val endDate: String? = null,
) {
    @Serializable
    data class VenueDto(
        @SerialName("name") val name: String? = null,
        @SerialName("address") val address: String? = null,
    )

    @Serializable
    data class PrizeDto(
        @SerialName("amount") val amount: Int? = null,
        @SerialName("currency") val currency: String? = null,
    )
}
