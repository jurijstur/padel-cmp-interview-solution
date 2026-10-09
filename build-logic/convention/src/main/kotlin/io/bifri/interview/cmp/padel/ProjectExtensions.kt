package io.bifri.interview.cmp.padel

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun Project.findPluginId(alias: String): String = libs.findPlugin(alias).get().get().pluginId

@Suppress("EagerGradleConfiguration")
internal fun Project.runBeforePreBuild(vararg dependencies: Any) = tasks
    .matching {
        // Ensures that the dependency runs even when building from Android Studio
        it.name.endsWith("preBuild", ignoreCase = true)
    }
    .configureEach { dependsOn(dependencies) }
