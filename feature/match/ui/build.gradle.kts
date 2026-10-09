private val moduleNamespace = "io.bifri.interview.cmp.padel.feature.match.ui"

plugins {
    alias(libs.plugins.convention.uifeature)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.match.domain)
        }
    }
}

android {
    namespace = moduleNamespace
}

compose.resources {
    publicResClass = false
    generateResClass = always
    nameOfResClass = "MatchUiRes"
    packageOfResClass = moduleNamespace
}
