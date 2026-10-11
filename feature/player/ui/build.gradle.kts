private val moduleNamespace = "io.bifri.interview.cmp.padel.feature.player.ui"

plugins {
    alias(libs.plugins.convention.uifeature)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.player.domain)
        }
    }
}

android {
    namespace = moduleNamespace
}

compose.resources {
    publicResClass = false
    generateResClass = always
    nameOfResClass = "PlayerUiRes"
    packageOfResClass = moduleNamespace
}
