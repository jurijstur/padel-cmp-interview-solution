package io.bifri.interview.cmp.padel

import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureCoreDeps(extension: KotlinMultiplatformExtension) {
    extension.apply {
        sourceSets {
            commonMain.dependencies {
                implementation(project(":config"))
                implementation(project(":core:coroutines"))
                implementation(project(":core:exception"))
                implementation(project(":core:extensions"))
                implementation(project(":core:logging"))
            }
        }
    }
}
