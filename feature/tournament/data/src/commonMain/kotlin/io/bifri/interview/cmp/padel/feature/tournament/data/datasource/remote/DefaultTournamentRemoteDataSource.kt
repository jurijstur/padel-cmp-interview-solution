package io.bifri.interview.cmp.padel.feature.tournament.data.datasource.remote

import io.bifri.interview.cmp.padel.feature.tournament.data.datasource.model.TournamentResponseDto
import io.bifri.interview.cmp.padel.feature.tournament.data.datasource.model.TournamentsResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

internal class DefaultTournamentRemoteDataSource(private val httpClient: HttpClient) : TournamentRemoteDataSource {
    override suspend fun getTournaments(): TournamentsResponseDto =
        httpClient.get("/api/tournaments") {
            parameter("before_date", BeforeDate)
            parameter("per_page", PerPage)
            parameter("sort_by", SortBy)
            parameter("order_by", OrderBy)
        }
            .body()

    override suspend fun getTournament(id: Long): TournamentResponseDto = httpClient.get("/api/tournaments/$id").body()

    private companion object {
        const val BeforeDate = "2026-08-16"
        const val PerPage = 50
        const val SortBy = "start_date"
        const val OrderBy = "desc"
    }
}
