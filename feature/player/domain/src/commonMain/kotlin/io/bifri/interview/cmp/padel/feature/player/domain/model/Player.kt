package io.bifri.interview.cmp.padel.feature.player.domain.model

data class Player(
    val id: Long,
    val name: String,
    val shortName: String?,
    val photoUrl: String?,
    val nationality: String?,
    val ranking: String?,
    val points: Int?,
    val category: String?,
)
