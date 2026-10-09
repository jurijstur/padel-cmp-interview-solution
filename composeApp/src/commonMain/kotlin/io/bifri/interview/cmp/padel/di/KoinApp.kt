package io.bifri.interview.cmp.padel.di

import io.bifri.interview.cmp.padel.config.buildconfig.isReleaseBuildType
import io.bifri.interview.cmp.padel.core.logging.koinLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes

internal fun initKoin(additionalConfiguration: KoinAppDeclaration? = null): KoinApplication =
    startKoin {
        if (!isReleaseBuildType) logger(koinLogger)
        includes(additionalConfiguration)
        modules(AppModules)
    }
