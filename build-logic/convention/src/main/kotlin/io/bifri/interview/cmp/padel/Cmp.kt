package io.bifri.interview.cmp.padel

import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureCompose(extension: KotlinMultiplatformExtension) {
    extension.apply {
        sourceSets {
            commonMain.dependencies {
                implementation(libs.findLibrary("compose.runtime").get())
                implementation(libs.findLibrary("compose.ui.tooling.preview").get())
            }
        }

        configureLanguageSettingsCompose()
    }
}
