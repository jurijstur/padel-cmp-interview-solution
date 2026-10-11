package io.bifri.interview.cmp.padel.feature.player.data.repository

import io.bifri.interview.cmp.padel.feature.player.data.datasource.model.PlayersResponseDto

interface PlayerRepository {
    suspend fun getPlayers(): PlayersResponseDto
}
