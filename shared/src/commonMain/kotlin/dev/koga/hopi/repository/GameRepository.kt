package dev.koga.hopi.repository

import dev.koga.hopi.di.BASE_URL
import dev.koga.hopi.model.Category
import dev.koga.hopi.model.GameDetails
import dev.koga.hopi.model.Resource
import dev.koga.hopi.model.SimpleGame
import dev.koga.hopi.model.SortOptions
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.request
import kotlinx.coroutines.flow.flow

class GameRepository(
    private val client: HttpClient
) {

    fun getAll(
        sortOptions: SortOptions? = null,
        category: Category? = null,
    ) = flow {
        emit(Resource.Loading)

        val response = try {
            val response = client.get("$BASE_URL/games") {
                category?.let {
                    parameter("category", it.key)
                }

                sortOptions?.order?.let {
                    parameter("sort-by", sortOptions.order.key)
                }

                sortOptions?.platform?.let {
                    parameter("platform", sortOptions.platform.key)
                }
            }.body<List<SimpleGame>>()

            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error
        }

        emit(response)
    }

    fun getById(id: Int) = flow {
        emit(Resource.Loading)

        val response = try {
            val response = client.get("$BASE_URL/game?id=${id}").body<GameDetails>()
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error
        }

        emit(response)
    }
}