package io.bifri.interview.cmp.padel.core.coroutines.di

import io.bifri.interview.cmp.padel.core.coroutines.di.CoroutinesDiQualifier.ApplicationScopeDefault
import io.bifri.interview.cmp.padel.core.coroutines.di.CoroutinesDiQualifier.ApplicationScopeIo
import io.bifri.interview.cmp.padel.core.coroutines.di.CoroutinesDiQualifier.DefaultDispatcher
import io.bifri.interview.cmp.padel.core.coroutines.di.CoroutinesDiQualifier.IoDispatcher
import io.bifri.interview.cmp.padel.core.coroutines.di.CoroutinesDiQualifier.MainDispatcher
import io.bifri.interview.cmp.padel.core.coroutines.di.CoroutinesDiQualifier.MainImmediateDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val CoroutinesModule = module {
    single(DefaultDispatcher.koinQualifier) { Dispatchers.Default }
    single(IoDispatcher.koinQualifier) { Dispatchers.IO }
    single(MainDispatcher.koinQualifier) { Dispatchers.Main }
    single(MainImmediateDispatcher.koinQualifier) { Dispatchers.Main.immediate }
    single(ApplicationScopeDefault.koinQualifier) {
        val defaultDispatcher: CoroutineDispatcher = get(DefaultDispatcher.koinQualifier)
        CoroutineScope(SupervisorJob() + defaultDispatcher)
    }
    single(ApplicationScopeIo.koinQualifier) {
        val ioDispatcher: CoroutineDispatcher = get(IoDispatcher.koinQualifier)
        CoroutineScope(SupervisorJob() + ioDispatcher)
    }
}
