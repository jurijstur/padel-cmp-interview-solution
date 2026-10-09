package io.bifri.interview.cmp.padel.core.extensions.di

import io.bifri.interview.cmp.padel.core.extensions.datetime.DateTimeUtils
import org.koin.dsl.module

val ExtensionsModule = module {
    single { DateTimeUtils() }
}
