private val moduleNamespace = "io.bifri.interview.cmp.padel.feature.player.domain"

plugins {
    alias(libs.plugins.convention.library)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.player.data)
        }
    }
}

android {
    namespace = moduleNamespace
}
