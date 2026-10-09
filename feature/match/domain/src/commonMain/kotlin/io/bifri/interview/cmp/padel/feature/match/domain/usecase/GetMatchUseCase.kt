package io.bifri.interview.cmp.padel.feature.match.domain.usecase

import io.bifri.interview.cmp.padel.feature.match.data.repository.MatchRepository
import io.bifri.interview.cmp.padel.feature.match.domain.mapper.MatchDomainMapper

class GetMatchUseCase(
    private val matchRepository: MatchRepository,
    private val mapper: MatchDomainMapper,
) {
    suspend operator fun invoke(matchId: Long) = with(mapper) {
        matchRepository.getMatch(matchId).toDomain()
    }
}
