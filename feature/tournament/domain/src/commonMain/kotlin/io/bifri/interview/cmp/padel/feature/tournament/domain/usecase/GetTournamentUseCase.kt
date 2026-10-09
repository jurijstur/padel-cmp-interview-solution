package io.bifri.interview.cmp.padel.feature.tournament.domain.usecase

import io.bifri.interview.cmp.padel.feature.tournament.data.repository.TournamentRepository
import io.bifri.interview.cmp.padel.feature.tournament.domain.mapper.TournamentDomainMapper

class GetTournamentUseCase(
    private val tournamentRepository: TournamentRepository,
    private val mapper: TournamentDomainMapper,
) {
    suspend operator fun invoke(tournamentId: Long) = with(mapper) {
        tournamentRepository.getTournament(tournamentId).toDomain()
    }
}
