package io.bifri.interview.cmp.padel.feature.player.ui.di

import io.bifri.interview.cmp.padel.core.coroutines.di.CoroutinesDiQualifier.IoDispatcher
import io.bifri.interview.cmp.padel.feature.player.ui.players.PlayersViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val PlayerUiModule = module {
    viewModel {
        PlayersViewModel(
            coroutineDispatcher = get(IoDispatcher.koinQualifier),
            exceptionHandler = get(),
            getPlayersUseCase = get(),
        )
    }
}
