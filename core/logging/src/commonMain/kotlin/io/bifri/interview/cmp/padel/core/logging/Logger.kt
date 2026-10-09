package io.bifri.interview.cmp.padel.core.logging

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.Severity.Error
import co.touchlab.kermit.Severity.Info
import co.touchlab.kermit.koin.KermitKoinLogger
import co.touchlab.kermit.loggerConfigInit
import co.touchlab.kermit.platformLogWriter
import io.ktor.client.plugins.logging.Logger as KtorLogger
import org.koin.core.logger.Logger as KoinLogger

val analyticsLogger: Logger
    get() {
        val params = LoggingParams.Analytics
        return Logger(
            loggerConfigInit(platformLogWriter(), minSeverity = params.severity),
            params.tag,
        )
    }

val appExceptionLogger: Logger
    get() {
        val params = LoggingParams.AppException
        return Logger(
            loggerConfigInit(platformLogWriter(), minSeverity = params.severity),
            params.tag,
        )
    }

val koinLogger: KoinLogger
    get() {
        val params = LoggingParams.Koin
        return KermitKoinLogger(
            Logger(
                loggerConfigInit(platformLogWriter(), minSeverity = params.severity),
                params.tag,
            ),
        )
    }

val ktorLogger: KtorLogger
    get() {
        val params = LoggingParams.Ktor
        val logger = Logger(
            loggerConfigInit(platformLogWriter(), minSeverity = params.severity),
            params.tag,
        )
        return object : KtorLogger {
            override fun log(message: String) {
                logger.i(message)
            }
        }
    }

private enum class LoggingParams(val severity: Severity, val tag: String) {
    Analytics(Info, "analytics"),
    AppException(Error, "app_exception"),
    Koin(Info, "koin"),
    Ktor(Info, "ktor"),
}
