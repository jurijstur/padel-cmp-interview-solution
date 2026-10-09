package io.bifri.interview.cmp.padel.feature.tournament.domain.mapper

import io.bifri.interview.cmp.padel.core.exception.ExceptionHandler
import io.bifri.interview.cmp.padel.core.extensions.datetime.DateTimeUtils
import io.bifri.interview.cmp.padel.feature.tournament.data.datasource.model.TournamentsResponseDto
import io.bifri.interview.cmp.padel.feature.tournament.data.datasource.model.TournamentsResponseDto.TournamentDto
import io.bifri.interview.cmp.padel.feature.tournament.domain.model.TournamentsTournament
import kotlinx.datetime.LocalDate

class TournamentsDomainMapper(
    private val exceptionHandler: ExceptionHandler,
    private val dateTimeUtils: DateTimeUtils,
) {
    fun TournamentsResponseDto.toDomain(): List<TournamentsTournament> = data
        ?.mapNotNull {
            try {
                it?.toDomain()
            } catch (e: IllegalArgumentException) {
                exceptionHandler.handleException(e)
                null
            }
        }
        .orEmpty()

    private fun TournamentDto.toDomain() = TournamentsTournament(
        id = requireNotNull(id),
        name = name,
        location = location,
        country = country,
        level = level,
        status = status,
        startDate = startDate?.toDateOrNull(),
        endDate = endDate?.toDateOrNull(),
    )

    private fun String.toDateOrNull(): LocalDate? = with(dateTimeUtils) { isoDate() }
}
