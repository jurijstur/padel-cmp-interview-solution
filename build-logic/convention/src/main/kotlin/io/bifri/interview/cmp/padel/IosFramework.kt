package io.bifri.interview.cmp.padel

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

internal fun KotlinMultiplatformExtension.configureIosFramework(name: String) {
    targets
        .filterIsInstance<KotlinNativeTarget>()
        .forEach { iosTarget ->
            iosTarget.binaries.framework {
                baseName = name
                isStatic = true
            }
        }
}
