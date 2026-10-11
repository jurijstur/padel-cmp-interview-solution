package io.bifri.interview.cmp.padel.feature.player.data.datasource.remote

import io.bifri.interview.cmp.padel.feature.player.data.datasource.model.PlayersResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

internal class DefaultPlayerRemoteDataSource(
    private val httpClient: HttpClient,
) : PlayerRemoteDataSource {
    override suspend fun getPlayers(): PlayersResponseDto = httpClient.get("/api/players") {
        parameter("per_page", PerPage)
        parameter("sort_by", SortBy)
        parameter("order_by", OrderBy)
    }
        .body()

    private companion object {
        const val PerPage = 50
        const val SortBy = "name"
        const val OrderBy = "asc"
    }
}
