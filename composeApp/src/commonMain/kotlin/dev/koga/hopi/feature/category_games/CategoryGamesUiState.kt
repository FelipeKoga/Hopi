package dev.koga.hopi.feature.category_games

import dev.koga.hopi.model.Category
import dev.koga.hopi.model.Resource
import dev.koga.hopi.model.SimpleGame
import dev.koga.hopi.model.SortOptions

data class CategoryGamesUiState(
    val category: Category,
    val gamesUiState: Resource<List<SimpleGame>>,
    val sortOptions: SortOptions = SortOptions.empty,
)