package io.bifri.interview.cmp.padel

import com.android.build.api.dsl.ApplicationExtension
import io.bifri.interview.cmp.padel.Version.Companion.fromVersionProperties
import org.gradle.api.Project
import java.util.Properties

internal fun Project.configureAndroidAppVersion(extension: ApplicationExtension) {
    val version = fromVersionProperties()
    extension.defaultConfig {
        versionCode = version.code
        versionName = version.name
    }
}

internal data class Version(
    private val major: Int,
    private val minor: Int,
    private val patch: Int,
    private val build: Int,
) {
    val code get() = major * 1000000 + minor * 10000 + patch * 100 + build

    val name get() = "$major.$minor.$patch.$build"

    internal companion object {
        internal fun Project.fromVersionProperties(): Version {
            val versionProps = loadVersionProperties()

            val major = versionProps.getProperty("MAJOR").toInt()
            val minor = versionProps.getProperty("MINOR").toInt()
            val patch = versionProps.getProperty("PATCH").toInt()
            val build = versionProps.getProperty("BUILD").toInt()

            return Version(major, minor, patch, build)
        }

        private fun Project.loadVersionProperties(): Properties {
            val versionFile = rootProject.file("config/version.properties")
            require(versionFile.exists()) { "version.properties not found at ${versionFile.absolutePath}" }

            return Properties().apply {
                versionFile.inputStream().use(::load)
            }
        }
    }
}
