package io.bifri.interview.cmp.padel.core.network

import io.bifri.interview.cmp.padel.config.buildconfig.isReleaseBuildType
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.bearerAuth
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.seconds

internal typealias KtorLogger = io.ktor.client.plugins.logging.Logger

private val RequestTimeoutMillis = 60.seconds.inWholeMilliseconds
private val ConnectTimeoutMillis = 60.seconds.inWholeMilliseconds
private val SocketTimeoutMillis = 60.seconds.inWholeMilliseconds

internal fun httpClient(
    json: Json,
    logger: KtorLogger,
    baseUrl: String,
    apiToken: String,
) = HttpClient {
    expectSuccess = true

    defaultRequest {
        url(baseUrl)
        bearerAuth(apiToken)
    }

    install(HttpTimeout) {
        requestTimeoutMillis = RequestTimeoutMillis
        connectTimeoutMillis = ConnectTimeoutMillis
        socketTimeoutMillis = SocketTimeoutMillis
    }

    install(ContentNegotiation) {
        json(json)
    }

    if (!isReleaseBuildType) {
        install(Logging) {
            this.logger = logger
            level = LogLevel.ALL
        }
    }
}
