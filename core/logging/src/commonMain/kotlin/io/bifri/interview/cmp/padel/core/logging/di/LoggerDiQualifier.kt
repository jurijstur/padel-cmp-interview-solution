package io.bifri.interview.cmp.padel.core.logging.di

import org.koin.core.qualifier.named

enum class LoggerDiQualifier {
    AnalyticsLogger,
    AppExceptionLogger,
    ;

    val koinQualifier = named(this)
}
