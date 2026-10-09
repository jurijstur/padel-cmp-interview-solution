package io.bifri.interview.cmp.padel.feature.tournament.domain.di

import io.bifri.interview.cmp.padel.feature.tournament.domain.mapper.TournamentDomainMapper
import io.bifri.interview.cmp.padel.feature.tournament.domain.mapper.TournamentsDomainMapper
import io.bifri.interview.cmp.padel.feature.tournament.domain.usecase.GetTournamentUseCase
import io.bifri.interview.cmp.padel.feature.tournament.domain.usecase.GetTournamentsUseCase
import org.koin.dsl.module

val TournamentDomainModule = module {
    single { GetTournamentsUseCase(tournamentRepository = get(), mapper = get()) }
    single { GetTournamentUseCase(tournamentRepository = get(), mapper = get()) }
    single { TournamentsDomainMapper(exceptionHandler = get(), dateTimeUtils = get()) }
    single { TournamentDomainMapper(dateTimeUtils = get()) }
}
