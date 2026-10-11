private val moduleNamespace = "io.bifri.interview.cmp.padel.feature.tournament.ui"

plugins {
    alias(libs.plugins.convention.uifeature)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.location)
            implementation(projects.feature.tournament.domain)
        }
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }
        commonTest.dependencies {
            implementation(libs.kotlinx.coroutines.test)
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
