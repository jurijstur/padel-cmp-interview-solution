package io.bifri.interview.cmp.padel.feature.player.domain.mapper

import io.bifri.interview.cmp.padel.core.exception.ExceptionHandler
import io.bifri.interview.cmp.padel.feature.player.data.datasource.model.PlayersResponseDto
import io.bifri.interview.cmp.padel.feature.player.data.datasource.model.PlayersResponseDto.PlayerDto
import io.bifri.interview.cmp.padel.feature.player.domain.model.Player

class PlayersDomainMapper(
    private val exceptionHandler: ExceptionHandler,
) {
    fun PlayersResponseDto.toDomain(): List<Player> = data
        ?.mapNotNull {
            try {
                it?.toDomain()
            } catch (e: IllegalArgumentException) {
                exceptionHandler.handleException(e)
                null
            }
        }
        .orEmpty()

    private fun PlayerDto.toDomain() = Player(
        id = requireNotNull(id),
        name = name.orEmpty(),
        shortName = shortName,
        photoUrl = photoUrl,
        nationality = nationality,
        ranking = ranking,
        points = points,
        category = category,
    )
}
