package io.bifri.interview.cmp.padel.core.network

import io.bifri.interview.cmp.padel.config.PadelApiConfig
import io.bifri.interview.cmp.padel.core.logging.ktorLogger
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val NetworkModule = module {
    single<KtorLogger> { ktorLogger }
    single<HttpClient> {
        val config = get<PadelApiConfig>()
        httpClient(
            json = get<Json>(),
            logger = get<KtorLogger>(),
            baseUrl = config.baseUrl,
            apiToken = config.apiToken,
        )
    }
    single<Json> { defaultJson() }
}
