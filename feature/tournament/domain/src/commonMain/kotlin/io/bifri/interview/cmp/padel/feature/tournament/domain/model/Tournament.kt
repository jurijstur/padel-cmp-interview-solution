package io.bifri.interview.cmp.padel.feature.tournament.domain.model

import kotlinx.datetime.LocalDate

data class Tournament(
    val id: Long,
    val name: String?,
    val location: String?,
    val country: String?,
    val timezone: String?,
    val level: String?,
    val status: String?,
    val venueName: String?,
    val venueAddress: String?,
    val prizeAmount: Int?,
    val prizeCurrency: String?,
    val photoUrl: String?,
    val courtType: String?,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
)
