private val moduleNamespace = "io.bifri.interview.cmp.padel.core.ui"

plugins {
    alias(libs.plugins.convention.library)
    alias(libs.plugins.convention.library.compose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.designsystem)
            api(libs.androidx.lifecycle.runtime.compose)
            api(libs.androidx.lifecycle.viewmodel.compose)
            api(libs.coil.compose)
            api(libs.coil.network.ktor)
            api(libs.koin.compose)
            api(libs.koin.compose.viewmodel)
            api(libs.navigation3.ui)
        }
    }
}

android {
    namespace = moduleNamespace
}
