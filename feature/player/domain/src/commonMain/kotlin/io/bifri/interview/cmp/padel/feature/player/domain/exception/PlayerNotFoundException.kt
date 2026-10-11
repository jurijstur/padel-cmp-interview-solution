package io.bifri.interview.cmp.padel.feature.player.domain.exception

class PlayerNotFoundException(
    val playerId: Long,
) : NoSuchElementException("Player $playerId was not found")
