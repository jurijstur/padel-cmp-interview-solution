package io.bifri.interview.cmp.padel.feature.match.data.datasource.remote

import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.MatchResponseDto
import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.MatchesResponseDto
import io.bifri.interview.cmp.padel.feature.match.data.datasource.model.PairDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

internal class DefaultMatchRemoteDataSource(
    private val httpClient: HttpClient,
) : MatchRemoteDataSource {

    override suspend fun getTournamentMatches(tournamentId: Long): MatchesResponseDto =
        httpClient.get("/api/tournaments/$tournamentId/matches") {
            parameter("per_page", PerPage)
            parameter("sort_by", SortBy)
            parameter("order_by", OrderBy)
        }
            .body()

    override suspend fun getMatch(id: Long): MatchResponseDto = httpClient.get("/api/matches/$id").body()

    override suspend fun getPair(path: String): PairDto = httpClient.get { url(path) }.body()

    private companion object {
        const val PerPage = 50
        const val SortBy = "updated_at"
        const val OrderBy = "desc"
    }
}
