private val moduleNamespace = "io.bifri.interview.cmp.padel.feature.player.data"

plugins {
    alias(libs.plugins.convention.library)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.network)
        }
    }
}

android {
    namespace = moduleNamespace
}
