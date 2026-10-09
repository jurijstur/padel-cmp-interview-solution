private val moduleNamespace = "io.bifri.interview.cmp.padel.core.designsystem"

plugins {
    alias(libs.plugins.convention.library.core)
    alias(libs.plugins.convention.library.compose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.coil.compose)
            api(libs.compose.components.resources)
            api(libs.compose.foundation)
            api(libs.compose.material.icons.extended)
            api(libs.compose.material3)
            api(libs.compose.runtime.saveable)
            api(libs.compose.ui)
            api(libs.compose.ui.backhandler)
        }
        androidMain.dependencies {
            api(libs.compose.ui.tooling)
            api(libs.compose.ui.tooling.preview)
            api(libs.androidx.activity.compose)
        }
    }
}

android {
    namespace = moduleNamespace
}

compose.resources {
    publicResClass = true
    generateResClass = always
    nameOfResClass = "DesignSystemRes"
    packageOfResClass = moduleNamespace
}
