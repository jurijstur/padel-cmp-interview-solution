private val moduleNamespace = "io.bifri.interview.cmp.padel"

plugins {
    alias(libs.plugins.convention.application)
    alias(libs.plugins.convention.application.compose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.config)
            implementation(projects.core.coroutines)
            implementation(projects.core.network)
            implementation(projects.core.ui)
            implementation(projects.feature.match.data)
            implementation(projects.feature.match.domain)
            implementation(projects.feature.match.ui)
            implementation(projects.feature.tournament.data)
            implementation(projects.feature.tournament.domain)
            implementation(projects.feature.tournament.ui)
        }
    }
}

android {
    namespace = moduleNamespace
}
