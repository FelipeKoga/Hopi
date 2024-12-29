package dev.koga.hopi

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Games : Route

    @Serializable
    data class GameDetails(val id: Int) : Route

    @Serializable
    data class CategoryGames(val categoryKey: String): Route
}