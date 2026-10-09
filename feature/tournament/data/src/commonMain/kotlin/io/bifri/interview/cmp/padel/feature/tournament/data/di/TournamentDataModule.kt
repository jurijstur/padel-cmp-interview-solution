package io.bifri.interview.cmp.padel.feature.tournament.data.di

import io.bifri.interview.cmp.padel.config.PadelApiConfig
import io.bifri.interview.cmp.padel.feature.tournament.data.datasource.demo.DemoTournamentDataSource
import io.bifri.interview.cmp.padel.feature.tournament.data.datasource.demo.DemoTournamentsDataSource
import io.bifri.interview.cmp.padel.feature.tournament.data.datasource.remote.DefaultTournamentRemoteDataSource
import io.bifri.interview.cmp.padel.feature.tournament.data.datasource.remote.TournamentRemoteDataSource
import io.bifri.interview.cmp.padel.feature.tournament.data.repository.DemoTournamentRepository
import io.bifri.interview.cmp.padel.feature.tournament.data.repository.PadelApiTournamentRepository
import io.bifri.interview.cmp.padel.feature.tournament.data.repository.TournamentRepository
import org.koin.dsl.module

val TournamentDataModule = module {
    single<TournamentRepository> {
        if (get<PadelApiConfig>().apiToken.isEmpty()) {
            DemoTournamentRepository(
                tournamentsDataSource = get(),
                tournamentDataSource = get(),
            )
        } else {
            PadelApiTournamentRepository(remoteDataSource = get())
        }
    }
    single<TournamentRemoteDataSource> { DefaultTournamentRemoteDataSource(httpClient = get()) }
    single<DemoTournamentsDataSource> { DemoTournamentsDataSource() }
    single<DemoTournamentDataSource> { DemoTournamentDataSource() }
}
