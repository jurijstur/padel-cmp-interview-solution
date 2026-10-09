package io.bifri.interview.cmp.padel.feature.match.domain.usecase

import io.bifri.interview.cmp.padel.feature.match.data.repository.MatchRepository
import io.bifri.interview.cmp.padel.feature.match.domain.mapper.MatchesDomainMapper

class GetTournamentMatchesUseCase(
    private val matchRepository: MatchRepository,
    private val mapper: MatchesDomainMapper,
) {
    suspend operator fun invoke(tournamentId: Long) = with(mapper) {
        matchRepository.getTournamentMatches(tournamentId).toDomain()
    }
}
