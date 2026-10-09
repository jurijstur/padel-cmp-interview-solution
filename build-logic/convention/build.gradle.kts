plugins {
    `kotlin-dsl`
    alias(libs.plugins.android.lint)
}

group = "io.bifri.interview.cmp.padel.buildlogic"

kotlin {
    jvmToolchain(libs.versions.jdk.get().toInt())
}

dependencies {
    compileOnly(libs.android.gradle.api.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    implementation(libs.detekt.gradle)
    implementation(libs.ktlint.gradle)
    // lint checks to verify usage of Gradle APIs
    lintChecks(libs.androidx.lint.gradle)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("conventionApplication") {
            id = libs.plugins.convention.application.asProvider().get().pluginId
            implementationClass = "ApplicationConventionPlugin"
        }

        register("conventionApplicationCompose") {
            id = libs.plugins.convention.application.compose.get().pluginId
            implementationClass = "ApplicationComposeConventionPlugin"
        }

        register("conventionLibrary") {
            id = libs.plugins.convention.library.asProvider().get().pluginId
            implementationClass = "LibraryConventionPlugin"
        }

        register("conventionLibraryCompose") {
            id = libs.plugins.convention.library.compose.get().pluginId
            implementationClass = "LibraryComposeConventionPlugin"
        }

        register("conventionLibraryCore") {
            id = libs.plugins.convention.library.core.get().pluginId
            implementationClass = "LibraryCoreConventionPlugin"
        }

        register("conventionUiFeature") {
            id = libs.plugins.convention.uifeature.get().pluginId
            implementationClass = "UiFeatureConventionPlugin"
        }

        register("conventionKoin") {
            id = libs.plugins.convention.koin.get().pluginId
            implementationClass = "KoinConventionPlugin"
        }

        register("conventionBignum") {
            id = libs.plugins.convention.bignum.get().pluginId
            implementationClass = "BignumConventionPlugin"
        }

        register("conventionFileKit") {
            id = libs.plugins.convention.filekit.get().pluginId
            implementationClass = "FileKitConventionPlugin"
        }

        register("conventionPadelApiConfig") {
            id = libs.plugins.convention.padelapiconfig.get().pluginId
            implementationClass = "PadelApiConfigConventionPlugin"
        }

        register("conventionLint") {
            id = libs.plugins.convention.lint.get().pluginId
            implementationClass = "LintConventionPlugin"
        }

        register("conventionKtlint") {
            id = libs.plugins.convention.ktlint.get().pluginId
            implementationClass = "KtLintConventionPlugin"
        }

        register("conventionDetekt") {
            id = libs.plugins.convention.detekt.get().pluginId
            implementationClass = "DetektConventionPlugin"
        }
    }
}
