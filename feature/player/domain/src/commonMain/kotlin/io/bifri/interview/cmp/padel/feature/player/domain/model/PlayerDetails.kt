package io.bifri.interview.cmp.padel.feature.player.domain.model

data class PlayerDetails(
    val id: Long,
    val name: String,
    val photoUrl: String?,
    val category: String?,
    val nationality: String?,
    val ranking: String?,
    val points: Int?,
    val elo: Int?,
    val height: Int?,
    val side: String?,
    val hand: String?,
    val birthplace: String?,
    val age: String?,
)
