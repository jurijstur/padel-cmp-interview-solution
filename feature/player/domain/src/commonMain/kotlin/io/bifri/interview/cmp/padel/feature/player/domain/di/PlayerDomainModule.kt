package io.bifri.interview.cmp.padel.feature.player.domain.di

import io.bifri.interview.cmp.padel.feature.player.domain.mapper.PlayerDetailsDomainMapper
import io.bifri.interview.cmp.padel.feature.player.domain.mapper.PlayersDomainMapper
import io.bifri.interview.cmp.padel.feature.player.domain.usecase.GetPlayerUseCase
import io.bifri.interview.cmp.padel.feature.player.domain.usecase.GetPlayersUseCase
import org.koin.dsl.module

val PlayerDomainModule = module {
    single { GetPlayersUseCase(playerRepository = get(), mapper = get()) }
    single { GetPlayerUseCase(playerRepository = get(), mapper = get()) }
    single { PlayersDomainMapper(exceptionHandler = get()) }
    single { PlayerDetailsDomainMapper() }
}
