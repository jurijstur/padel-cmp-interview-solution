package io.bifri.interview.cmp.padel.feature.match.data.datasource.demo

import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.MatchResponseDto
import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.MatchResponseDto.PlayersDto
import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.MatchResponseDto.PlayersDto.PlayerDto
import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.MatchResponseDto.PlayersDto.PlayerDto.PlayerConnectionsDto
import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.MatchResponseDto.ScoreDto

internal class DemoMatchDataSource {
    private val matches = mapOf(
        11546L to MatchResponseDto(
            id = 11546L,
            court = "Center Court",
            roundName = "Finals",
            players = PlayersDto(
                teamOne = listOf(
                    PlayerDto(
                        name = "Agustin Tapia",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/66-65"),
                    ),
                    PlayerDto(
                        name = "Arturo Coello",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/66-65"),
                    ),
                ),
                teamTwo = listOf(
                    PlayerDto(
                        name = "Federico Chingotto",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/114-115"),
                    ),
                    PlayerDto(
                        name = "Alejandro Galan",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/114-115"),
                    ),
                ),
            ),
            score = listOf(
                ScoreDto(
                    teamOne = "6",
                    teamTwo = "3",
                ),
                ScoreDto(
                    teamOne = "5",
                    teamTwo = "7",
                ),
                ScoreDto(
                    teamOne = "7",
                    teamTwo = "5",
                ),
            ),
            winner = "team_1",
        ),
        11577L to MatchResponseDto(
            id = 11577L,
            court = "Center Court",
            roundName = "Finals",
            players = PlayersDto(
                teamOne = listOf(
                    PlayerDto(
                        name = "Gemma Triay Pons",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/433-434"),
                    ),
                    PlayerDto(
                        name = "Delfina Brea Senesi",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/433-434"),
                    ),
                ),
                teamTwo = listOf(
                    PlayerDto(
                        name = "Claudia Fernandez Sanchez",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/412-469"),
                    ),
                    PlayerDto(
                        name = "Martina Calvo Santamaria",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/412-469"),
                    ),
                ),
            ),
            score = listOf(
                ScoreDto(
                    teamOne = "5",
                    teamTwo = "7",
                ),
                ScoreDto(
                    teamOne = "3",
                    teamTwo = "6",
                ),
            ),
            winner = "team_2",
        ),
        11312L to MatchResponseDto(
            id = 11312L,
            court = "Centre Court",
            roundName = "Finals",
            players = PlayersDto(
                teamOne = listOf(
                    PlayerDto(
                        name = "Javier Leal",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/95-82"),
                    ),
                    PlayerDto(
                        name = "Francisco Guerrero",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/95-82"),
                    ),
                ),
                teamTwo = listOf(
                    PlayerDto(
                        name = "Alejandro Galan",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/115-114"),
                    ),
                    PlayerDto(
                        name = "Federico Chingotto",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/115-114"),
                    ),
                ),
            ),
            score = listOf(
                ScoreDto(
                    teamOne = "2",
                    teamTwo = "6",
                ),
                ScoreDto(
                    teamOne = "7",
                    teamTwo = "5",
                ),
                ScoreDto(
                    teamOne = "6(7)",
                    teamTwo = "7",
                ),
            ),
            winner = "team_2",
        ),
        11343L to MatchResponseDto(
            id = 11343L,
            court = "Centre Court",
            roundName = "Finals",
            players = PlayersDto(
                teamOne = listOf(
                    PlayerDto(
                        name = "Gemma Triay Pons",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/433-434"),
                    ),
                    PlayerDto(
                        name = "Delfina Brea Senesi",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/433-434"),
                    ),
                ),
                teamTwo = listOf(
                    PlayerDto(
                        name = "Claudia Fernandez Sanchez",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/412-469"),
                    ),
                    PlayerDto(
                        name = "Martina Calvo Santamaria",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/412-469"),
                    ),
                ),
            ),
            score = listOf(
                ScoreDto(
                    teamOne = "6",
                    teamTwo = "2",
                ),
                ScoreDto(
                    teamOne = "6(5)",
                    teamTwo = "7",
                ),
                ScoreDto(
                    teamOne = "7",
                    teamTwo = "6(4)",
                ),
            ),
            winner = "team_1",
        ),
        11544L to MatchResponseDto(
            id = 11544L,
            court = "Center Court",
            roundName = "Semifinals",
            players = PlayersDto(
                teamOne = listOf(
                    PlayerDto(
                        name = "Agustin Tapia",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/66-65"),
                    ),
                    PlayerDto(
                        name = "Arturo Coello",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/66-65"),
                    ),
                ),
                teamTwo = listOf(
                    PlayerDto(
                        name = "Martin Di Nenno",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/103-77"),
                    ),
                    PlayerDto(
                        name = "Francisco Navarro",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/103-77"),
                    ),
                ),
            ),
            score = listOf(
                ScoreDto(
                    teamOne = "6",
                    teamTwo = "3",
                ),
                ScoreDto(
                    teamOne = "6",
                    teamTwo = "2",
                ),
            ),
            winner = "team_1",
        ),
        11545L to MatchResponseDto(
            id = 11545L,
            court = "Center Court",
            roundName = "Semifinals",
            players = PlayersDto(
                teamOne = listOf(
                    PlayerDto(
                        name = "Leo Augsburger",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/84-88"),
                    ),
                    PlayerDto(
                        name = "Juan Lebron",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/84-88"),
                    ),
                ),
                teamTwo = listOf(
                    PlayerDto(
                        name = "Federico Chingotto",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/114-115"),
                    ),
                    PlayerDto(
                        name = "Alejandro Galan",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/114-115"),
                    ),
                ),
            ),
            score = listOf(
                ScoreDto(
                    teamOne = "6(3)",
                    teamTwo = "7",
                ),
                ScoreDto(
                    teamOne = "3",
                    teamTwo = "6",
                ),
            ),
            winner = "team_2",
        ),
        11310L to MatchResponseDto(
            id = 11310L,
            court = "Centre Court",
            roundName = "Semifinals",
            players = PlayersDto(
                teamOne = listOf(
                    PlayerDto(
                        name = "Arturo Coello",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/65-66"),
                    ),
                    PlayerDto(
                        name = "Agustin Tapia",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/65-66"),
                    ),
                ),
                teamTwo = listOf(
                    PlayerDto(
                        name = "Javier Leal",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/95-82"),
                    ),
                    PlayerDto(
                        name = "Francisco Guerrero",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/95-82"),
                    ),
                ),
            ),
            score = listOf(
                ScoreDto(
                    teamOne = "5",
                    teamTwo = "7",
                ),
                ScoreDto(
                    teamOne = "5",
                    teamTwo = "7",
                ),
            ),
            winner = "team_2",
        ),
        11311L to MatchResponseDto(
            id = 11311L,
            court = "Centre Court",
            roundName = "Semifinals",
            players = PlayersDto(
                teamOne = listOf(
                    PlayerDto(
                        name = "Juanlu Esbri",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/153-96"),
                    ),
                    PlayerDto(
                        name = "Sanyo Gutierrez",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/153-96"),
                    ),
                ),
                teamTwo = listOf(
                    PlayerDto(
                        name = "Alejandro Galan",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/115-114"),
                    ),
                    PlayerDto(
                        name = "Federico Chingotto",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/115-114"),
                    ),
                ),
            ),
            score = listOf(
                ScoreDto(
                    teamOne = "5",
                    teamTwo = "7",
                ),
                ScoreDto(
                    teamOne = "4",
                    teamTwo = "6",
                ),
            ),
            winner = "team_2",
        ),
        11341L to MatchResponseDto(
            id = 11341L,
            court = "Centre Court",
            roundName = "Semifinals",
            players = PlayersDto(
                teamOne = listOf(
                    PlayerDto(
                        name = "Gemma Triay Pons",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/433-434"),
                    ),
                    PlayerDto(
                        name = "Delfina Brea Senesi",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/433-434"),
                    ),
                ),
                teamTwo = listOf(
                    PlayerDto(
                        name = "Alejandra Salazar Bengoechea",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/424-406"),
                    ),
                    PlayerDto(
                        name = "Aranzazu Osoro Ulrich",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/424-406"),
                    ),
                ),
            ),
            score = listOf(
                ScoreDto(
                    teamOne = "6",
                    teamTwo = "2",
                ),
                ScoreDto(
                    teamOne = "6",
                    teamTwo = "1",
                ),
            ),
            winner = "team_1",
        ),
        11540L to MatchResponseDto(
            id = 11540L,
            court = "Center Court",
            roundName = "Quarter",
            players = PlayersDto(
                teamOne = listOf(
                    PlayerDto(
                        name = "Agustin Tapia",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/66-65"),
                    ),
                    PlayerDto(
                        name = "Arturo Coello",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/66-65"),
                    ),
                ),
                teamTwo = listOf(
                    PlayerDto(
                        name = "Mariano Gonzalez",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/180-148"),
                    ),
                    PlayerDto(
                        name = "Francisco Cabeza Teres",
                        connections = PlayerConnectionsDto(pair = "/api/pairs/180-148"),
                    ),
                ),
            ),
            score = listOf(
                ScoreDto(
                    teamOne = "6",
                    teamTwo = "1",
                ),
                ScoreDto(
                    teamOne = "6",
                    teamTwo = "2",
                ),
            ),
            winner = "team_1",
        ),
    )

    fun getMatch(id: Long): MatchResponseDto = matches[id] ?: matchFallback(id)

    private fun matchFallback(id: Long) = MatchResponseDto(
        id = id,
        court = "Unknown Court",
        roundName = "Unknown Round",
        players = PlayersDto(
            teamOne = listOf(
                PlayerDto(name = "Player 1"),
                PlayerDto(name = "Player 2"),
            ),
            teamTwo = listOf(
                PlayerDto(name = "Player 3"),
                PlayerDto(name = "Player 4"),
            ),
        ),
        winner = null,
    )
}
