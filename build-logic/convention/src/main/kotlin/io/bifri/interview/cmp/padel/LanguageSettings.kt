package io.bifri.interview.cmp.padel

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun KotlinMultiplatformExtension.configureLanguageSettings() {
    sourceSets.all {
        languageSettings {
            optIn("kotlin.time.ExperimentalTime")
            optIn("kotlin.uuid.ExperimentalUuidApi")
            optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
            optIn("kotlinx.coroutines.FlowPreview")
        }
    }
}

internal fun KotlinMultiplatformExtension.configureLanguageSettingsCompose() {
    sourceSets.all {
        languageSettings {
            optIn("androidx.compose.material3.ExperimentalMaterial3Api")
            optIn("androidx.compose.material3.ExperimentalMaterial3ExpressiveApi")
            optIn("androidx.compose.ui.ExperimentalComposeUiApi")
        }
    }
}
