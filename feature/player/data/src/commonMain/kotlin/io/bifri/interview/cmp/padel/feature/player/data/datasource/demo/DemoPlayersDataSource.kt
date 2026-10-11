package io.bifri.interview.cmp.padel.feature.player.data.datasource.demo

import io.bifri.interview.cmp.padel.feature.player.data.datasource.model.PlayersResponseDto
import io.bifri.interview.cmp.padel.feature.player.data.datasource.model.PlayersResponseDto.PlayerDto

internal class DemoPlayersDataSource {
    private val players = PlayersResponseDto(
        data = listOf(
            player(id = 1L, name = "Arturo Coello", shortName = "Coello", nationality = "ES", ranking = "1"),
            player(id = 2L, name = "Agustin Tapia", shortName = "Tapia", nationality = "AR", ranking = "2"),
            player(id = 3L, name = "Alejandro Galan", shortName = "Galan", nationality = "ES", ranking = "3"),
            player(id = 4L, name = "Alejandro Arroyo", shortName = "Arroyo", nationality = "ES", ranking = "18"),
            player(id = 5L, name = "Federico Chingotto", shortName = "Chingotto", nationality = "AR", ranking = "4"),
            player(
                id = 6L,
                name = "Gemma Triay",
                shortName = "Triay",
                nationality = "ES",
                ranking = "1",
                category = "women",
            ),
            player(
                id = 7L,
                name = "Delfina Brea",
                shortName = "Brea",
                nationality = "AR",
                ranking = "2",
                category = "women",
            ),
            player(
                id = 8L,
                name = "Paula Josemaria",
                shortName = "Josemaria",
                nationality = "ES",
                ranking = "6",
                category = "women",
            ),
        ),
    )

    fun getPlayers(): PlayersResponseDto = players

    private fun player(
        id: Long,
        name: String,
        shortName: String,
        nationality: String,
        ranking: String,
        category: String = "men",
    ) = PlayerDto(
        id = id,
        name = name,
        shortName = shortName,
        photoUrl = null,
        nationality = nationality,
        ranking = ranking,
        points = null,
        category = category,
    )
}
