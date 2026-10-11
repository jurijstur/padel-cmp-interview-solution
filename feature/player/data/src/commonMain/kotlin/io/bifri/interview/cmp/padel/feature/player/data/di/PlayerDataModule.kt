package io.bifri.interview.cmp.padel.feature.player.data.di

import io.bifri.interview.cmp.padel.config.PadelApiConfig
import io.bifri.interview.cmp.padel.feature.player.data.datasource.demo.DemoPlayersDataSource
import io.bifri.interview.cmp.padel.feature.player.data.datasource.remote.DefaultPlayerRemoteDataSource
import io.bifri.interview.cmp.padel.feature.player.data.datasource.remote.PlayerRemoteDataSource
import io.bifri.interview.cmp.padel.feature.player.data.repository.DemoPlayerRepository
import io.bifri.interview.cmp.padel.feature.player.data.repository.PadelApiPlayerRepository
import io.bifri.interview.cmp.padel.feature.player.data.repository.PlayerRepository
import org.koin.dsl.module

val PlayerDataModule = module {
    single<PlayerRepository> {
        if (get<PadelApiConfig>().apiToken.isEmpty()) {
            DemoPlayerRepository(playersDataSource = get())
        } else {
            PadelApiPlayerRepository(remoteDataSource = get())
        }
    }
    single<PlayerRemoteDataSource> { DefaultPlayerRemoteDataSource(httpClient = get()) }
    single<DemoPlayersDataSource> { DemoPlayersDataSource() }
}
