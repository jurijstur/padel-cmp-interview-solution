package io.bifri.interview.cmp.padel.feature.player.data.datasource.remote

import io.bifri.interview.cmp.padel.feature.player.data.datasource.model.PlayerResponseDto
import io.bifri.interview.cmp.padel.feature.player.data.datasource.model.PlayersResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode

internal class DefaultPlayerRemoteDataSource(
    private val httpClient: HttpClient,
) : PlayerRemoteDataSource {
    override suspend fun getPlayers(): PlayersResponseDto = httpClient.get("/api/players") {
        parameter("per_page", PerPage)
        parameter("sort_by", SortBy)
        parameter("order_by", OrderBy)
    }
        .body()

    override suspend fun getPlayer(playerId: Long): PlayerResponseDto? = try {
        httpClient.get("/api/players/$playerId").body()
    } catch (exception: ClientRequestException) {
        if (exception.response.status == HttpStatusCode.NotFound) {
            null
        } else {
            throw exception
        }
    }

    private companion object {
        const val PerPage = 50
        const val SortBy = "name"
        const val OrderBy = "asc"
    }
}
