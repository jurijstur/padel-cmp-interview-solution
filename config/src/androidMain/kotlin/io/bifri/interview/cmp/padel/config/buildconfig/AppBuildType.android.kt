package io.bifri.interview.cmp.padel.config.buildconfig

import io.bifri.interview.cmp.padel.config.BuildConfig
import io.bifri.interview.cmp.padel.config.buildconfig.AppBuildType.Debug
import io.bifri.interview.cmp.padel.config.buildconfig.AppBuildType.Release

@Suppress("KotlinConstantConditions")
actual val appBuildType: AppBuildType
    get() = when (BuildConfig.BUILD_TYPE) {
        AppBuildTypeStr.Debug -> Debug
        AppBuildTypeStr.Release -> Release
        else -> error("Unsupported build type: ${BuildConfig.BUILD_TYPE}")
    }

private object AppBuildTypeStr {
    const val Debug = "debug"
    const val Release = "release"
}
