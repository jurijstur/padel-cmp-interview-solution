package io.bifri.interview.cmp.padel.core.ui

import kotlin.concurrent.Volatile

/**
 * https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
 * https://gist.github.com/JoseAlcerreca/e0bba240d9b3cffa258777f12e5c0ae9
 *
 * Used as a wrapper for data that is exposed via observable that represents an event.
 * This class is not thread-safe.
 */
class Event<out T>(private val content: T) {

    @Volatile
    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? =
        if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        other ?: return false
        if (this::class != other::class) return false
        other as Event<*>
        if (content != other.content) return false
        if (hasBeenHandled != other.hasBeenHandled) return false
        return true
    }

    override fun hashCode(): Int {
        var result = content?.hashCode() ?: 0
        result = 31 * result + hasBeenHandled.hashCode()
        return result
    }

    override fun toString(): String = "Event [content=$content, hasBeenHandled=$hasBeenHandled"
}
