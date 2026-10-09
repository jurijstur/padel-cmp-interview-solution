package io.bifri.interview.cmp.padel

import org.gradle.api.Project
import java.util.Properties

internal data class AppFlavorConfig(
    val applicationId: String,
    val appName: String,
    val apkBaseName: String,
) {
    companion object {
        internal fun Project.fromAppFlavorProperties(): AppFlavorConfig {
            val configFile = rootProject.file("config/app-flavor.properties")
            require(configFile.exists()) { "app-flavor.properties not found at ${configFile.absolutePath}" }

            val props = Properties().apply { configFile.inputStream().use(::load) }

            return AppFlavorConfig(
                applicationId = props.getProperty("applicationId"),
                appName = props.getProperty("appName"),
                apkBaseName = props.getProperty("apkBaseName"),
            )
        }
    }
}
