package io.bifri.interview.cmp.padel.feature.player.data.repository

import io.bifri.interview.cmp.padel.feature.player.data.datasource.demo.DemoPlayersDataSource

internal class DemoPlayerRepository(
    private val playersDataSource: DemoPlayersDataSource,
) : PlayerRepository {
    override suspend fun getPlayers() = playersDataSource.getPlayers()
}
