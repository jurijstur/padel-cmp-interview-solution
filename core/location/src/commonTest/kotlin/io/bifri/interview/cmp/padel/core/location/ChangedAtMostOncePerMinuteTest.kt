package io.bifri.interview.cmp.padel.core.location

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ChangedAtMostOncePerMinuteTest {
    @Test
    fun keepsTheLatestFixUntilTheIntervalEnds() = runTest {
        val readings = flow {
            emit(firstFix)
            delay(HalfIntervalMillis)
            emit(movedFix)
            delay(HalfIntervalMillis)
        }.changedAtMostOncePerMinute().toList()

        assertEquals(listOf(firstFix, movedFix), readings)
    }

    @Test
    fun duplicateReadingDoesNotRestartTheInterval() = runTest {
        val readings = flow {
            emit(firstFix)
            delay(IntervalMillis + HalfIntervalMillis)
            emit(firstFix)
            delay(HalfIntervalMillis)
            emit(movedFix)
        }.changedAtMostOncePerMinute().toList()

        assertEquals(listOf(firstFix, movedFix), readings)
    }

    private companion object {
        const val IntervalMillis = 60_000L
        const val HalfIntervalMillis = 30_000L
        val firstFix = deviceLocation(latitude = 51.5074, longitude = -0.1278)
        val movedFix = deviceLocation(latitude = 41.3826, longitude = 2.1771)
    }
}
