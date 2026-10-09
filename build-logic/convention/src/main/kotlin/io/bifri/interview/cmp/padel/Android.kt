package io.bifri.interview.cmp.padel

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project

internal fun Project.configureAndroid(extension: CommonExtension) {
    extension.apply {
        compileSdk {
            version = release(libs.findVersion("androidCompileSdk").get().requiredVersion.toInt()) {
                minorApiLevel = libs.findVersion("androidCompileSdkMinor").get().requiredVersion.toInt()
            }
        }

        defaultConfig.minSdk = libs.findVersion("androidMinSdk").get().requiredVersion.toInt()

        compileOptions.apply {
            sourceCompatibility(JavaVersion.VERSION_21)
            targetCompatibility(JavaVersion.VERSION_21)
        }
    }
}
