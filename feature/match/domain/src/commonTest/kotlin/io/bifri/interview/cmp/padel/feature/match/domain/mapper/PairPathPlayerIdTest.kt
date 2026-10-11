package io.bifri.interview.cmp.padel.feature.match.domain.mapper

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@Suppress("MagicNumber")
class PairPathPlayerIdTest {

    @Test
    fun readsIdsInTeamOrder() {
        assertEquals(2L, playerIdFromPairPath("/api/pairs/2-1", playerIndex = 0))
        assertEquals(1L, playerIdFromPairPath("/api/pairs/2-1", playerIndex = 1))
    }

    @Test
    fun returnsNullWhenThePathHasNoIdAtThatIndex() {
        assertNull(playerIdFromPairPath("/api/pairs/2-1", playerIndex = 2))
        assertNull(playerIdFromPairPath("/api/pairs/not-ids", playerIndex = 0))
        assertNull(playerIdFromPairPath(null, playerIndex = 0))
        assertNull(playerIdFromPairPath("/api/pairs/2-1", playerIndex = -1))
    }
}
