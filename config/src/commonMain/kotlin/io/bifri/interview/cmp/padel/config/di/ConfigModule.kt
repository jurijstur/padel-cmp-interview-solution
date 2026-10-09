package io.bifri.interview.cmp.padel.config.di

import io.bifri.interview.cmp.padel.config.PadelApiConfig
import io.bifri.interview.cmp.padel.config.padelApiConfig
import org.koin.dsl.module

val ConfigModule = module {
    single<PadelApiConfig> { padelApiConfig() }
}
