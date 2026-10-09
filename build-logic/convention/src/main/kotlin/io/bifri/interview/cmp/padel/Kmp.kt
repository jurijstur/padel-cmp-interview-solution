package io.bifri.interview.cmp.padel

import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureKmp(extension: KotlinMultiplatformExtension) {
    extension.apply {
        androidTarget {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_21)
            }
        }
        iosArm64()
        iosSimulatorArm64()

        sourceSets {
            commonMain.dependencies {
                implementation(libs.findLibrary("flowext").get())
                implementation(libs.findLibrary("kotlin.stdlib").get())
                implementation(libs.findLibrary("kotlinx.coroutines").get())
                implementation(libs.findLibrary("kotlinx.datetime").get())
                implementation(libs.findLibrary("kotlinx.serialization.json").get())
            }
            commonTest.dependencies {
                implementation(libs.findLibrary("kotlin.test").get())
            }
        }

        configureLanguageSettings()
    }
}
