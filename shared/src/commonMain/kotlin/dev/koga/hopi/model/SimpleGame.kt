package dev.koga.hopi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimpleGame(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("short_description")
    val description: String,
    @SerialName("thumbnail")
    val thumbnail: String,
)
