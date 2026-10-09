package io.bifri.interview.cmp.padel.core.logging.di

import io.bifri.interview.cmp.padel.core.logging.analyticsLogger
import io.bifri.interview.cmp.padel.core.logging.appExceptionLogger
import io.bifri.interview.cmp.padel.core.logging.di.LoggerDiQualifier.AnalyticsLogger
import io.bifri.interview.cmp.padel.core.logging.di.LoggerDiQualifier.AppExceptionLogger
import org.koin.dsl.module

val LoggerModule = module {
    single(AnalyticsLogger.koinQualifier) { analyticsLogger }
    single(AppExceptionLogger.koinQualifier) { appExceptionLogger }
}
