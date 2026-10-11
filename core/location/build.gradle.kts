private val moduleNamespace = "io.bifri.interview.cmp.padel.core.location"

plugins {
    alias(libs.plugins.convention.library.core)
}

kotlin {
    sourceSets {
        commonTest.dependencies {
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}

android {
    namespace = moduleNamespace
}
