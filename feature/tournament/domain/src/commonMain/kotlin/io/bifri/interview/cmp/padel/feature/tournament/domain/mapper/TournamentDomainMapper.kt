package io.bifri.interview.cmp.padel.feature.tournament.domain.mapper

import io.bifri.interview.cmp.padel.core.extensions.datetime.DateTimeUtils
import io.bifri.interview.cmp.padel.feature.tournament.data.datasource.model.TournamentResponseDto
import io.bifri.interview.cmp.padel.feature.tournament.domain.model.Tournament
import kotlinx.datetime.LocalDate

class TournamentDomainMapper(
    private val dateTimeUtils: DateTimeUtils,
) {
    fun TournamentResponseDto.toDomain(): Tournament = Tournament(
        id = requireNotNull(id),
        name = name,
        location = location,
        country = country,
        timezone = timezone,
        level = level,
        status = status,
        venueName = venue?.name,
        venueAddress = venue?.address,
        prizeAmount = prize?.amount,
        prizeCurrency = prize?.currency,
        photoUrl = photoUrl,
        courtType = courtType,
        startDate = startDate?.toDateOrNull(),
        endDate = endDate?.toDateOrNull(),
    )

    private fun String.toDateOrNull(): LocalDate? = with(dateTimeUtils) { isoDate() }
}
