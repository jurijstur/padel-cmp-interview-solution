package io.bifri.interview.cmp.padel.feature.tournament.domain.usecase

import io.bifri.interview.cmp.padel.feature.tournament.data.repository.TournamentRepository
import io.bifri.interview.cmp.padel.feature.tournament.domain.mapper.TournamentsDomainMapper

class GetTournamentsUseCase(
    private val tournamentRepository: TournamentRepository,
    private val mapper: TournamentsDomainMapper,
) {
    suspend operator fun invoke() = with(mapper) { tournamentRepository.getTournaments().toDomain() }
}
