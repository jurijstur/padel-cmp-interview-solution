package io.bifri.interview.cmp.padel.config.buildconfig

enum class AppBuildType {
    Debug,
    Release,
}

expect val appBuildType: AppBuildType

val isReleaseBuildType get() = appBuildType == AppBuildType.Release
