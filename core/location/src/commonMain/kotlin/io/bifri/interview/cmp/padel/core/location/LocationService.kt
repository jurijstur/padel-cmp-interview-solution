package io.bifri.interview.cmp.padel.core.location

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

interface LocationService {
    fun observe(): Flow<DeviceLocation>
}

internal const val MinimumLocationIntervalMillis = 60_000L

internal fun Flow<DeviceLocation>.changedAtMostOncePerMinute(): Flow<DeviceLocation> = channelFlow {
    var lastEmittedKnown: DeviceLocation.Known? = null
    var pendingKnown: DeviceLocation.Known? = null
    var windowJob: Job? = null

    fun armWindow() {
        windowJob = launch {
            delay(MinimumLocationIntervalMillis)
            val deferred = pendingKnown
            pendingKnown = null
            if (deferred != null && deferred != lastEmittedKnown) {
                send(deferred)
                lastEmittedKnown = deferred
                armWindow()
            }
        }
    }

    suspend fun emitKnown(reading: DeviceLocation.Known) {
        if (reading == lastEmittedKnown) return
        send(reading)
        lastEmittedKnown = reading
        pendingKnown = null
        windowJob?.cancel()
        armWindow()
    }

    collect { reading ->
        when (reading) {
            is DeviceLocation.Known -> {
                val windowRunning = windowJob?.isActive == true
                if (!windowRunning) {
                    emitKnown(reading)
                } else if (reading != lastEmittedKnown) {
                    pendingKnown = reading
                }
            }

            else -> {
                windowJob?.cancel()
                windowJob = null
                pendingKnown = null
                send(reading)
            }
        }
    }
}.distinctUntilChanged()
