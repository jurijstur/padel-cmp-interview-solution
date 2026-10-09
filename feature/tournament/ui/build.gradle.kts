private val moduleNamespace = "io.bifri.interview.cmp.padel.feature.tournament.ui"

plugins {
    alias(libs.plugins.convention.uifeature)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.tournament.domain)
        }
    }
}

android {
    namespace = moduleNamespace
}

compose.resources {
    publicResClass = false
    generateResClass = always
    nameOfResClass = "TournamentUiRes"
    packageOfResClass = moduleNamespace
}
