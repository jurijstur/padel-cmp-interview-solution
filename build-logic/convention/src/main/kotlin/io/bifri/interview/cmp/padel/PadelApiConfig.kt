package io.bifri.interview.cmp.padel

import org.gradle.api.Project
import java.util.Properties

internal data class PadelApiConfig(
    val apiToken: String,
) {

    companion object {

        internal fun Project.fromPadelApiProperties(): PadelApiConfig? {
            val padelApiConfig = loadPadelApiProperties()
            val apiToken = padelApiConfig?.getProperty("apiToken")
            return if (apiToken != null) PadelApiConfig(apiToken) else null
        }

        private fun Project.loadPadelApiProperties(): Properties? {
            val configFile = rootProject.file("config/padelapi.properties")

            return if (configFile.exists()) {
                Properties().apply {
                    configFile.inputStream().use(::load)
                }
            } else {
                null
            }
        }
    }
}
