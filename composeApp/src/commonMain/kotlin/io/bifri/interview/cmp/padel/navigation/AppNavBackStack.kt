package io.bifri.interview.cmp.padel.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.savedstate.serialization.SavedStateConfiguration
import io.bifri.interview.cmp.padel.navigation.AppRoute.MatchGraph
import io.bifri.interview.cmp.padel.navigation.AppRoute.TournamentGraph
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

private val AppNavConfiguration = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(baseClass = NavKey::class) {
            subclass(TournamentGraph::class)
            subclass(MatchGraph::class)
        }
    }
}

@Composable
internal fun rememberAppNavBackStack(): NavBackStack<NavKey> =
    rememberNavBackStack(AppNavConfiguration, TournamentGraph)
