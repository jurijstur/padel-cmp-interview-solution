package io.bifri.interview.cmp.padel.feature.tournament.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.bifri.interview.cmp.padel.core.designsystem.theme.dimens
import io.bifri.interview.cmp.padel.feature.tournament.domain.model.TournamentsTournament
import io.bifri.interview.cmp.padel.feature.tournament.ui.TournamentUiRes
import io.bifri.interview.cmp.padel.feature.tournament.ui.tournaments_distance_away
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun TournamentSummary(
    tournament: TournamentsTournament,
    modifier: Modifier = Modifier,
    distanceKm: Int? = null,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimens.spacingSmall),
    ) {
        val tournamentName = tournament.name
        if (tournamentName != null) {
            Text(
                text = tournamentName,
                style = MaterialTheme.typography.titleLarge,
            )
        }
        Text(
            text = tournament.formatLevelAndStatus(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        val locationLine = tournament.locationLine(distanceKm)
        if (locationLine.isNotBlank()) {
            Text(text = locationLine)
        }
        Text(
            text = tournament.formatDateRange(),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

private fun TournamentsTournament.formatLevelAndStatus() = listOfNotNull(level, status)
    .filter { it.isNotBlank() }
    .joinToString(" • ") { it.replace('_', ' ').uppercase() }

@Composable
private fun TournamentsTournament.locationLine(distanceKm: Int?): String {
    val place = listOfNotNull(location, country).filter { it.isNotBlank() }.joinToString(", ")
    val distance = distanceKm?.let { kilometers ->
        stringResource(TournamentUiRes.string.tournaments_distance_away, kilometers)
    }
    return listOf(place, distance.orEmpty()).filter { it.isNotBlank() }.joinToString(" • ")
}

private fun TournamentsTournament.formatDateRange() = listOfNotNull(startDate, endDate).joinToString(" – ")
