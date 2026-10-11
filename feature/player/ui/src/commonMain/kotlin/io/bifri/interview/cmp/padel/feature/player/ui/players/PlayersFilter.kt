package io.bifri.interview.cmp.padel.feature.player.ui.players

import io.bifri.interview.cmp.padel.feature.player.domain.model.Player

internal fun filterPlayersByName(players: List<Player>, query: String): List<Player> {
    val needle = query.trim()
    if (needle.isEmpty()) return players
    return players.filter { player -> player.name.contains(needle, ignoreCase = true) }
}
