package io.bifri.interview.cmp.padel.di

import io.bifri.interview.cmp.padel.config.di.ConfigModule
import io.bifri.interview.cmp.padel.core.coroutines.di.CoroutinesModule
import io.bifri.interview.cmp.padel.core.exception.di.ExceptionModule
import io.bifri.interview.cmp.padel.core.extensions.di.ExtensionsModule
import io.bifri.interview.cmp.padel.core.logging.di.LoggerModule
import io.bifri.interview.cmp.padel.core.network.NetworkModule
import io.bifri.interview.cmp.padel.feature.match.data.di.MatchDataModule
import io.bifri.interview.cmp.padel.feature.match.domain.di.MatchDomainModule
import io.bifri.interview.cmp.padel.feature.match.ui.di.MatchUiModule
import io.bifri.interview.cmp.padel.feature.tournament.data.di.TournamentDataModule
import io.bifri.interview.cmp.padel.feature.tournament.domain.di.TournamentDomainModule
import io.bifri.interview.cmp.padel.feature.tournament.ui.di.TournamentUiModule
import org.koin.core.module.Module

val AppModules: List<Module> = listOf(
    ConfigModule,
    CoroutinesModule,
    ExceptionModule,
    ExtensionsModule,
    LoggerModule,
    NetworkModule,
    MatchDataModule,
    MatchDomainModule,
    MatchUiModule,
    TournamentDataModule,
    TournamentDomainModule,
    TournamentUiModule,
)
