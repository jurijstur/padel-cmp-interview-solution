@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("build-logic")

    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
                includeGroupAndSubgroups("org.chromium")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
                includeGroupAndSubgroups("org.chromium")
            }
        }
        mavenCentral()
    }
}

rootProject.name = "padel-cmp-interview"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":composeApp")
include(":config")
include(":core:coroutines")
include(":core:designsystem")
include(":core:exception")
include(":core:extensions")
include(":core:logging")
include(":core:network")
include(":core:ui")
include(":feature:match:data")
include(":feature:match:domain")
include(":feature:match:ui")
include(":feature:tournament:data")
include(":feature:tournament:domain")
include(":feature:tournament:ui")
