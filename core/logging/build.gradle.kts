private val moduleNamespace = "io.bifri.interview.cmp.padel.core.logging"

plugins {
    alias(libs.plugins.convention.library.core)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.kermit)
            implementation(libs.kermit.koin)
            implementation(libs.ktor.client.logging)
        }
    }
}

android {
    namespace = moduleNamespace
}
