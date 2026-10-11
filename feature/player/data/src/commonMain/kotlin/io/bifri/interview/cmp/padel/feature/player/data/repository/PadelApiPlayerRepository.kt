package io.bifri.interview.cmp.padel.feature.player.data.repository

import io.bifri.interview.cmp.padel.feature.player.data.datasource.remote.PlayerRemoteDataSource

internal class PadelApiPlayerRepository(
    private val remoteDataSource: PlayerRemoteDataSource,
) : PlayerRepository {
    override suspend fun getPlayers() = remoteDataSource.getPlayers()
}
