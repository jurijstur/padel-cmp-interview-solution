package io.bifri.interview.cmp.padel.core.location.di

import org.koin.core.module.Module
import org.koin.dsl.module

val LocationModule = module {
    registerLocationService()
}

internal expect fun Module.registerLocationService()
