private val moduleNamespace = "io.bifri.interview.cmp.padel.core.exception"

plugins {
    alias(libs.plugins.convention.library.core)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.config)
            implementation(projects.core.logging)
        }
    }
}

android {
    namespace = moduleNamespace
}
