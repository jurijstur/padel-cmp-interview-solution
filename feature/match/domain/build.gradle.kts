private val moduleNamespace = "io.bifri.interview.cmp.padel.feature.match.domain"

plugins {
    alias(libs.plugins.convention.library)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.match.data)
        }
    }
}

android {
    namespace = moduleNamespace
}
