package io.bifri.interview.cmp.padel.feature.player.domain.mapper

import io.bifri.interview.cmp.padel.feature.player.data.datasource.model.PlayerResponseDto
import io.bifri.interview.cmp.padel.feature.player.domain.model.PlayerDetails

class PlayerDetailsDomainMapper {
    fun PlayerResponseDto.toDomain() = PlayerDetails(
        id = requireNotNull(id),
        name = name.orEmpty(),
        photoUrl = photoUrl,
        category = category,
        nationality = nationality,
        ranking = ranking,
        points = points,
        elo = elo,
        height = height,
        side = side,
        hand = hand,
        birthplace = birthplace,
        age = age,
    )
}
