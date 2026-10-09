package io.bifri.interview.cmp.padel.core.ui.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

fun NavBackStack<NavKey>.pop() = removeLastOrNull()
