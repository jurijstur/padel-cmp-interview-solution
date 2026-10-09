package io.bifri.interview.cmp.padel.core.coroutines.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

fun <T> mutableSharedFlowOfInput(
    replay: Int = 0,
    extraBufferCapacity: Int = 1,
    onBufferOverflow: BufferOverflow = BufferOverflow.DROP_OLDEST,
): MutableSharedFlow<T> = MutableSharedFlow(
    replay = replay,
    extraBufferCapacity = extraBufferCapacity,
    onBufferOverflow = onBufferOverflow,
)

fun <T> Flow<T>.stateInViewModel(
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.Eagerly,
    initialValue: T,
): StateFlow<T> = stateIn(
    scope = scope,
    started = started,
    initialValue = initialValue,
)
