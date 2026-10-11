package io.bifri.interview.cmp.padel.feature.player.domain.usecase

import io.bifri.interview.cmp.padel.feature.player.data.repository.PlayerRepository
import io.bifri.interview.cmp.padel.feature.player.domain.mapper.PlayersDomainMapper

class GetPlayersUseCase(
    private val playerRepository: PlayerRepository,
    private val mapper: PlayersDomainMapper,
) {
    suspend operator fun invoke() = with(mapper) { playerRepository.getPlayers().toDomain() }
}
