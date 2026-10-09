package io.bifri.interview.cmp.padel.feature.tournament.domain.model

import kotlinx.datetime.LocalDate

data class TournamentsTournament(
    val id: Long,
    val name: String?,
    val location: String?,
    val country: String?,
    val level: String?,
    val status: String?,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
)
