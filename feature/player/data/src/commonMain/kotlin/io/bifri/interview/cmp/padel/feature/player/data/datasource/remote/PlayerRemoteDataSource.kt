package io.bifri.interview.cmp.padel.feature.player.data.datasource.remote

import io.bifri.interview.cmp.padel.feature.player.data.datasource.model.PlayerResponseDto
import io.bifri.interview.cmp.padel.feature.player.data.datasource.model.PlayersResponseDto

internal interface PlayerRemoteDataSource {
    suspend fun getPlayers(): PlayersResponseDto

    suspend fun getPlayer(playerId: Long): PlayerResponseDto?
}
