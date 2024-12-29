package dev.koga.hopi.model

import kotlinx.serialization.Serializable

@Serializable
data class SortOptions(
    val platform: Platform?,
    val order: OrderOption?
) {
    val count = when {
        platform != null && order != null -> 2
        platform != null || order != null -> 1
        else -> 0
    }

    companion object {
        val empty = SortOptions(null, null)
    }
}

@Serializable
enum class Platform(val key: String) {
    PC("pc"),
    BROWSER("browser"),
    ALL("all")
}

@Serializable
enum class OrderOption(
    val key: String
) {
    RELEASE_DATE("release-date"),
    ALPHABETICAL("alphabetical"),
    RELEVANCE("relevance")
}