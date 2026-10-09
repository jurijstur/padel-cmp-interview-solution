package io.bifri.interview.cmp.padel.core.ui.base

import androidx.lifecycle.ViewModel
import io.bifri.interview.cmp.padel.core.exception.ExceptionHandler
import kotlinx.coroutines.CoroutineDispatcher

abstract class BaseViewModel(
    protected val coroutineDispatcher: CoroutineDispatcher,
    protected val exceptionHandler: ExceptionHandler,
) : ViewModel()
