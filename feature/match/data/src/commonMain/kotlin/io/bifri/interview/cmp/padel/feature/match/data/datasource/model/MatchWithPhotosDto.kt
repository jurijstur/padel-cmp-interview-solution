package io.bifri.interview.cmp.padel.feature.match.data.datasource.model

data class MatchWithPhotosDto(
    val match: MatchResponseDto,
    val teamOnePlayerPhotoUrls: List<String?>,
    val teamTwoPlayerPhotoUrls: List<String?>,
    val teamOnePlayerIds: List<Long?> = emptyList(),
    val teamTwoPlayerIds: List<Long?> = emptyList(),
    val usePairPathPlayerIds: Boolean = false,
)
