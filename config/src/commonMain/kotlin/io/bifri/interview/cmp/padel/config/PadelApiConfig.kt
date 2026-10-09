package io.bifri.interview.cmp.padel.config

data class PadelApiConfig(
    val apiToken: String,
) {
    val baseUrl: String get() = BaseUrl

    companion object {
        private const val BaseUrl = "https://padelapi.org"
    }
}

internal expect fun padelApiConfig(): PadelApiConfig
