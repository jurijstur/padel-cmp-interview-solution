package io.bifri.interview.cmp.padel.feature.match.data.di

import io.bifri.interview.cmp.padel.config.PadelApiConfig
import io.bifri.interview.cmp.padel.feature.match.data.datasource.demo.DemoMatchDataSource
import io.bifri.interview.cmp.padel.feature.match.data.datasource.demo.DemoMatchesDataSource
import io.bifri.interview.cmp.padel.feature.match.data.datasource.remote.DefaultMatchRemoteDataSource
import io.bifri.interview.cmp.padel.feature.match.data.datasource.remote.MatchRemoteDataSource
import io.bifri.interview.cmp.padel.feature.match.data.repository.DemoMatchRepository
import io.bifri.interview.cmp.padel.feature.match.data.repository.MatchRepository
import io.bifri.interview.cmp.padel.feature.match.data.repository.PadelApiMatchRepository
import org.koin.dsl.module

val MatchDataModule = module {
    single<MatchRepository> {
        if (get<PadelApiConfig>().apiToken.isEmpty()) {
            DemoMatchRepository(
                demoMatchDataSource = get(),
                demoMatchesDataSource = get(),
            )
        } else {
            PadelApiMatchRepository(
                exceptionHandler = get(),
                remoteDataSource = get(),
            )
        }
    }
    single<MatchRemoteDataSource> { DefaultMatchRemoteDataSource(httpClient = get()) }
    single<DemoMatchDataSource> { DemoMatchDataSource() }
    single<DemoMatchesDataSource> { DemoMatchesDataSource() }
}
