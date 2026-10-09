private val moduleNamespace = "io.bifri.interview.cmp.padel.feature.tournament.domain"

plugins {
    alias(libs.plugins.convention.library)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.tournament.data)
        }
    }
}

android {
    namespace = moduleNamespace
}
