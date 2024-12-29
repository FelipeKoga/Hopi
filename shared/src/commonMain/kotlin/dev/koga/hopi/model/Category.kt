package dev.koga.hopi.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val key: String,
    val label: String,
) {
    companion object {
        val all = listOf(
            Category(key = "mmorpg", label = "MMORPG"),
            Category(key = "shooter", label = "Shooter"),
            Category(key = "strategy", label = "Strategy"),
            Category(key = "moba", label = "MOBA"),
            Category(key = "racing", label = "Racing"),
            Category(key = "sports", label = "Sports"),
            Category(key = "social", label = "Social"),
            Category(key = "sandbox", label = "Sandbox"),
            Category(key = "open-world", label = "Open-World"),
            Category(key = "survival", label = "Survival"),
            Category(key = "pvp", label = "PvP"),
            Category(key = "pve", label = "PvE"),
            Category(key = "pixel", label = "Pixel"),
            Category(key = "voxel", label = "Voxel"),
            Category(key = "zombie", label = "Zombie"),
            Category(key = "turn-based", label = "Turn-Based"),
            Category(key = "first-person", label = "First-Person"),
            Category(key = "third-person", label = "Third-Person"),
            Category(key = "top-down", label = "Top-Down"),
            Category(key = "tank", label = "Tank"),
            Category(key = "space", label = "Space"),
            Category(key = "sailing", label = "Sailing"),
            Category(key = "side-scroller", label = "Side-Scroller"),
            Category(key = "superhero", label = "Superhero"),
            Category(key = "permadeath", label = "Permadeath"),
            Category(key = "card", label = "Card"),
            Category(key = "battle-royale", label = "Battle-Royale"),
            Category(key = "mmo", label = "MMO"),
            Category(key = "mmofps", label = "MMOFPS"),
            Category(key = "mmotps", label = "MMOTPS"),
            Category(key = "3d", label = "3D"),
            Category(key = "2d", label = "2D"),
            Category(key = "anime", label = "Anime"),
            Category(key = "fantasy", label = "Fantasy"),
            Category(key = "sci-fi", label = "Sci-Fi"),
            Category(key = "fighting", label = "Fighting"),
            Category(key = "action-rpg", label = "Action-RPG"),
            Category(key = "action", label = "Action"),
            Category(key = "military", label = "Military"),
            Category(key = "martial-arts", label = "Martial-Arts"),
            Category(key = "flight", label = "Flight"),
            Category(key = "low-spec", label = "Low-Spec"),
            Category(key = "tower-defense", label = "Tower-Defense"),
            Category(key = "horror", label = "Horror"),
            Category(key = "mmorts", label = "MMORTS")
        )
    }
}

