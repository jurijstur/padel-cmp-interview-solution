package io.bifri.interview.cmp.padel.core.exception.di

import io.bifri.interview.cmp.padel.core.exception.DefaultExceptionHandler
import io.bifri.interview.cmp.padel.core.exception.ExceptionHandler
import io.bifri.interview.cmp.padel.core.logging.di.LoggerDiQualifier.AppExceptionLogger
import org.koin.dsl.module

val ExceptionModule = module {
    single<ExceptionHandler> {
        DefaultExceptionHandler(
            logger = get(AppExceptionLogger.koinQualifier),
        )
    }
}
