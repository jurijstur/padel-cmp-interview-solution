package io.bifri.interview.cmp.padel.feature.match.data.datasource.demo

import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.MatchesResponseDto
import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.MatchesResponseDto.MatchDto
import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.MatchesResponseDto.MatchDto.PlayersDto
import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.MatchesResponseDto.MatchDto.PlayersDto.PlayerDto
import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.MatchesResponseDto.MatchDto.ScoreDto

internal class DemoMatchesDataSource {
    private val matches = MatchesResponseDto(
        data = listOf(
            MatchDto(
                id = 11546L,
                court = "Center Court",
                courtOrder = 2,
                roundName = "Finals",
                status = "finished",
                players = PlayersDto(
                    teamOne = listOf(
                        PlayerDto(name = "Agustin Tapia"),
                        PlayerDto(name = "Arturo Coello"),
                    ),
                    teamTwo = listOf(
                        PlayerDto(name = "Federico Chingotto"),
                        PlayerDto(name = "Alejandro Galan"),
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
                playedAt = "2026-08-09T17:30:00 02:00",
                scheduledAt = "2026-08-09T17:30:00 02:00",
            ),
            MatchDto(
                id = 11577L,
                court = "Center Court",
                courtOrder = 1,
                roundName = "Finals",
                status = "finished",
                players = PlayersDto(
                    teamOne = listOf(
                        PlayerDto(name = "Gemma Triay Pons"),
                        PlayerDto(name = "Delfina Brea Senesi"),
                    ),
                    teamTwo = listOf(
                        PlayerDto(name = "Claudia Fernandez Sanchez"),
                        PlayerDto(name = "Martina Calvo Santamaria"),
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
                playedAt = "2026-08-09T15:00:00 02:00",
                scheduledAt = "2026-08-09T15:00:00 02:00",
            ),
            MatchDto(
                id = 11312L,
                court = "Centre Court",
                courtOrder = 2,
                roundName = "Finals",
                status = "finished",
                players = PlayersDto(
                    teamOne = listOf(
                        PlayerDto(name = "Javier Leal"),
                        PlayerDto(name = "Francisco Guerrero"),
                    ),
                    teamTwo = listOf(
                        PlayerDto(name = "Alejandro Galan"),
                        PlayerDto(name = "Federico Chingotto"),
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
                playedAt = "2026-08-02T16:00:00 02:00",
                scheduledAt = "2026-08-02T16:00:00 02:00",
            ),
            MatchDto(
                id = 11343L,
                court = "Centre Court",
                courtOrder = 1,
                roundName = "Finals",
                status = "finished",
                players = PlayersDto(
                    teamOne = listOf(
                        PlayerDto(name = "Gemma Triay Pons"),
                        PlayerDto(name = "Delfina Brea Senesi"),
                    ),
                    teamTwo = listOf(
                        PlayerDto(name = "Claudia Fernandez Sanchez"),
                        PlayerDto(name = "Martina Calvo Santamaria"),
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
                playedAt = "2026-08-02T13:00:00 02:00",
                scheduledAt = "2026-08-02T13:00:00 02:00",
            ),
            MatchDto(
                id = 11544L,
                court = "Center Court",
                courtOrder = 3,
                roundName = "Semifinals",
                status = "finished",
                players = PlayersDto(
                    teamOne = listOf(
                        PlayerDto(name = "Agustin Tapia"),
                        PlayerDto(name = "Arturo Coello"),
                    ),
                    teamTwo = listOf(
                        PlayerDto(name = "Martin Di Nenno"),
                        PlayerDto(name = "Francisco Navarro"),
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
                playedAt = "2026-08-08T19:00:00 02:00",
                scheduledAt = "2026-08-08T19:00:00 02:00",
            ),
            MatchDto(
                id = 11545L,
                court = "Center Court",
                courtOrder = 4,
                roundName = "Semifinals",
                status = "finished",
                players = PlayersDto(
                    teamOne = listOf(
                        PlayerDto(name = "Leo Augsburger"),
                        PlayerDto(name = "Juan Lebron"),
                    ),
                    teamTwo = listOf(
                        PlayerDto(name = "Federico Chingotto"),
                        PlayerDto(name = "Alejandro Galan"),
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
                playedAt = "2026-08-08T20:30:00 02:00",
                scheduledAt = "2026-08-08T20:30:00 02:00",
            ),
            MatchDto(
                id = 11310L,
                court = "Centre Court",
                courtOrder = 4,
                roundName = "Semifinals",
                status = "finished",
                players = PlayersDto(
                    teamOne = listOf(
                        PlayerDto(name = "Arturo Coello"),
                        PlayerDto(name = "Agustin Tapia"),
                    ),
                    teamTwo = listOf(
                        PlayerDto(name = "Javier Leal"),
                        PlayerDto(name = "Francisco Guerrero"),
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
                playedAt = "2026-08-01T19:00:00 02:00",
                scheduledAt = "2026-08-01T19:00:00 02:00",
            ),
            MatchDto(
                id = 11311L,
                court = "Centre Court",
                courtOrder = 3,
                roundName = "Semifinals",
                status = "finished",
                players = PlayersDto(
                    teamOne = listOf(
                        PlayerDto(name = "Juanlu Esbri"),
                        PlayerDto(name = "Sanyo Gutierrez"),
                    ),
                    teamTwo = listOf(
                        PlayerDto(name = "Alejandro Galan"),
                        PlayerDto(name = "Federico Chingotto"),
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
                playedAt = "2026-08-01T17:00:00 02:00",
                scheduledAt = "2026-08-01T17:00:00 02:00",
            ),
            MatchDto(
                id = 11540L,
                court = "Center Court",
                courtOrder = 3,
                roundName = "Quarter",
                status = "finished",
                players = PlayersDto(
                    teamOne = listOf(
                        PlayerDto(name = "Agustin Tapia"),
                        PlayerDto(name = "Arturo Coello"),
                    ),
                    teamTwo = listOf(
                        PlayerDto(name = "Mariano Gonzalez"),
                        PlayerDto(name = "Francisco Cabeza Teres"),
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
                playedAt = "2026-08-07T15:00:00 02:00",
                scheduledAt = "2026-08-07T15:00:00 02:00",
            ),
            MatchDto(
                id = 11306L,
                court = "Centre Court",
                courtOrder = 7,
                roundName = "Quarter",
                status = "finished",
                players = PlayersDto(
                    teamOne = listOf(
                        PlayerDto(name = "Arturo Coello"),
                        PlayerDto(name = "Agustin Tapia"),
                    ),
                    teamTwo = listOf(
                        PlayerDto(name = "Jose Jimenez"),
                        PlayerDto(name = "Alejandro Arroyo"),
                    ),
                ),
                playedAt = "2026-07-31T21:00:00 02:00",
                scheduledAt = "2026-07-31T21:00:00 02:00",
            ),
            MatchDto(
                id = 11516L,
                court = "Center Court",
                courtOrder = 2,
                roundName = "Round of 16",
                status = "finished",
                players = PlayersDto(
                    teamOne = listOf(
                        PlayerDto(name = "Agustin Tapia"),
                        PlayerDto(name = "Arturo Coello"),
                    ),
                    teamTwo = listOf(
                        PlayerDto(name = "Nuno Deus"),
                        PlayerDto(name = "Miguel Deus"),
                    ),
                ),
                playedAt = "2026-08-05T13:00:00 02:00",
                scheduledAt = "2026-08-05T13:00:00 02:00",
            ),
            MatchDto(
                id = 11298L,
                court = "Centre Court",
                courtOrder = 7,
                roundName = "Round of 16",
                status = "finished",
                players = PlayersDto(
                    teamOne = listOf(
                        PlayerDto(name = "Arturo Coello"),
                        PlayerDto(name = "Agustin Tapia"),
                    ),
                    teamTwo = listOf(
                        PlayerDto(name = "Edu Alonso"),
                        PlayerDto(name = "Aimar Goñi"),
                    ),
                ),
                playedAt = "2026-07-30T21:30:00 02:00",
                scheduledAt = "2026-07-30T21:30:00 02:00",
            ),
        ),
    )

    fun getMatches(): MatchesResponseDto = matches
}
