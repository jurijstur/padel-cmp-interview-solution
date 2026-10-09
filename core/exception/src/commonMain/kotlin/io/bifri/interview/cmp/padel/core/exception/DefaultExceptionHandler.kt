package io.bifri.interview.cmp.padel.core.exception

import co.touchlab.kermit.Logger
import io.bifri.interview.cmp.padel.config.buildconfig.isReleaseBuildType

internal class DefaultExceptionHandler(
    private val logger: Logger,
) : ExceptionHandler {
    override fun handleException(throwable: Throwable) {
        if (!isReleaseBuildType) {
            logger.e(
                messageString = throwable.message.orEmpty(),
                throwable = throwable,
            )
        }
    }
}
