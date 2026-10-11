package io.bifri.interview.cmp.padel.feature.player.domain.usecase

import io.bifri.interview.cmp.padel.feature.player.data.repository.PlayerRepository
import io.bifri.interview.cmp.padel.feature.player.domain.exception.PlayerNotFoundException
import io.bifri.interview.cmp.padel.feature.player.domain.mapper.PlayerDetailsDomainMapper

class GetPlayerUseCase(
    private val playerRepository: PlayerRepository,
    private val mapper: PlayerDetailsDomainMapper,
) {
    suspend operator fun invoke(playerId: Long) = playerRepository.getPlayer(playerId)
        ?.let { with(mapper) { it.toDomain() } }
        ?: throw PlayerNotFoundException(playerId)
}
