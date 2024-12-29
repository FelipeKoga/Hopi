package dev.koga.hopi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameDetails(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("status")
    val status: String,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("game_url")
    val gameUrl: String,
    val genre: String,
    val platform: String,
    val publisher: String,
    val developer: String,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("profile_url")
    val profileUrl: String,
    @SerialName("minimum_system_requirements")
    val minimumSystemRequirements: MinimumSystemRequirements,
    val screenshots: List<Screenshot>
)

@Serializable
data class Screenshot(
    val id: Int,
    val image: String,
)

@Serializable
data class MinimumSystemRequirements(
    val os: String,
    val processor: String,
    val memory: String,
    val graphics: String,
    val storage: String,
)