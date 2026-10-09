package io.bifri.interview.cmp.padel.core.extensions.datetime

import kotlinx.datetime.LocalDate

class DateTimeUtils {
    fun String.isoDate(): LocalDate? = try {
        LocalDate.parse(
            input = this,
            format = LocalDate.Formats.ISO,
        )
    } catch (_: IllegalArgumentException) {
        null
    }
}
