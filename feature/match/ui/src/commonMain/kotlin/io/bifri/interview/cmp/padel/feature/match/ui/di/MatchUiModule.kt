package io.bifri.interview.cmp.padel.feature.match.ui.di

import io.bifri.interview.cmp.padel.core.coroutines.di.CoroutinesDiQualifier.IoDispatcher
import io.bifri.interview.cmp.padel.feature.match.ui.match.MatchViewModel
import io.bifri.interview.cmp.padel.feature.match.ui.matches.MatchesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val MatchUiModule = module {
    viewModel { (tournamentId: Long, tournamentName: String) ->
        MatchesViewModel(
            tournamentId = tournamentId,
            tournamentName = tournamentName,
            coroutineDispatcher = get(IoDispatcher.koinQualifier),
            exceptionHandler = get(),
            getTournamentMatchesUseCase = get(),
        )
    }
    viewModel { (matchId: Long, tournamentName: String, totalCourts: Int?) ->
        MatchViewModel(
            matchId = matchId,
            tournamentName = tournamentName,
            totalCourts = totalCourts,
            coroutineDispatcher = get(IoDispatcher.koinQualifier),
            exceptionHandler = get(),
            getMatchUseCase = get(),
        )
    }
}
