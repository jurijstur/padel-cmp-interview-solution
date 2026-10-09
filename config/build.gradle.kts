private val moduleNamespace = "io.bifri.interview.cmp.padel.config"

plugins {
    alias(libs.plugins.convention.library.core)
    alias(libs.plugins.convention.padelapiconfig)
}

android {
    namespace = moduleNamespace
    buildFeatures {
        buildConfig = true
    }
}
