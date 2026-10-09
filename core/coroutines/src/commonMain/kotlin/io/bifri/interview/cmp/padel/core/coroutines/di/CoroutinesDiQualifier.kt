package io.bifri.interview.cmp.padel.core.coroutines.di

import org.koin.core.qualifier.named

enum class CoroutinesDiQualifier {
    DefaultDispatcher,
    IoDispatcher,
    MainDispatcher,
    MainImmediateDispatcher,
    ApplicationScopeDefault,
    ApplicationScopeIo,
    ;

    val koinQualifier = named(this)
}
