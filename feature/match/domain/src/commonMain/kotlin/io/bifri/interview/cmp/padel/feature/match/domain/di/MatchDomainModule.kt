package io.bifri.interview.cmp.padel.feature.match.domain.di

import io.bifri.interview.cmp.padel.feature.match.domain.mapper.MatchDomainMapper
import io.bifri.interview.cmp.padel.feature.match.domain.mapper.MatchesDomainMapper
import io.bifri.interview.cmp.padel.feature.match.domain.usecase.GetMatchUseCase
import io.bifri.interview.cmp.padel.feature.match.domain.usecase.GetTournamentMatchesUseCase
import org.koin.dsl.module

val MatchDomainModule = module {
    single<MatchesDomainMapper> {
        MatchesDomainMapper(
            exceptionHandler = get(),
            dateTimeUtils = get(),
        )
    }
    single<MatchDomainMapper> { MatchDomainMapper() }
    single<GetTournamentMatchesUseCase> {
        GetTournamentMatchesUseCase(
            matchRepository = get(),
            mapper = get(),
        )
    }
    single<GetMatchUseCase> {
        GetMatchUseCase(
            matchRepository = get(),
            mapper = get(),
        )
    }
}
