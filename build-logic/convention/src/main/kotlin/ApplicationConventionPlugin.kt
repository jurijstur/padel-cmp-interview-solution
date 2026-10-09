import com.android.build.api.dsl.ApplicationExtension
import io.bifri.interview.cmp.padel.AppFlavorConfig.Companion.fromAppFlavorProperties
import io.bifri.interview.cmp.padel.Version.Companion.fromVersionProperties
import io.bifri.interview.cmp.padel.configureAndroid
import io.bifri.interview.cmp.padel.configureAndroidAppVersion
import io.bifri.interview.cmp.padel.configureCoreDeps
import io.bifri.interview.cmp.padel.configureIosFramework
import io.bifri.interview.cmp.padel.configureKmp
import io.bifri.interview.cmp.padel.findPluginId
import io.bifri.interview.cmp.padel.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePluginExtension
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("unused")
internal class ApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(findPluginId("kotlin.multiplatform"))
                apply(findPluginId("android.application"))
                apply(findPluginId("kotlin.serialization"))
                apply(findPluginId("convention.detekt"))
                apply(findPluginId("convention.ktlint"))
                apply(findPluginId("convention.lint"))
                apply(findPluginId("convention.bignum"))
                apply(findPluginId("convention.filekit"))
                apply(findPluginId("convention.koin"))
            }

            extensions.configure<KotlinMultiplatformExtension>(::configureKmp)
            extensions.configure<KotlinMultiplatformExtension> { configureIosFramework("ComposeApp") }
            extensions.configure<KotlinMultiplatformExtension>(::configureCoreDeps)
            extensions.configure<ApplicationExtension>(::configureAndroidApplication)
        }
    }
}

private fun Project.configureAndroidApplication(extension: ApplicationExtension) {
    val appFlavorConfig = fromAppFlavorProperties()

    extension.apply {
        configureAndroid(this)
        configureAndroidAppVersion(this)

        defaultConfig {
            applicationId = appFlavorConfig.applicationId
            targetSdk = libs.findVersion("androidTargetSdk").get().requiredVersion.toInt()
        }
        buildTypes {
            debug {
                applicationIdSuffix = ".debug"
                resValue("string", "app_name", "${appFlavorConfig.appName} Debug")
            }
            release {
                applicationIdSuffix = ""
                isMinifyEnabled = true
                isShrinkResources = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro",
                )
                resValue("string", "app_name", appFlavorConfig.appName)
            }
        }
        buildFeatures {
            buildConfig = true
            resValues = true
        }
    }

    extensions.configure<BasePluginExtension> {
        val apkBaseName = appFlavorConfig.apkBaseName
        val versionName = fromVersionProperties().name
        archivesName.set("$apkBaseName-$versionName")
    }
}
