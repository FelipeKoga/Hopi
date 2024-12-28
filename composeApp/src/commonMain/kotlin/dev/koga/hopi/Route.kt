package dev.koga.hopi

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Home : Route

    @Serializable
    data class GameDetails(val id: Int) : Route
}