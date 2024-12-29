package dev.koga.hopi.repository

import dev.koga.hopi.BASE_URL
import dev.koga.hopi.model.GameDetails
import dev.koga.hopi.model.SimpleGame
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.flow


class MmoRepository(
    private val client: HttpClient
) {

    fun getAll() = flow {
        val response = client.get("$BASE_URL/games")
        emit(response.body<List<SimpleGame>>())
    }

    fun getById(id: Int) = flow {
        val response = client.get("$BASE_URL/game?id=${id}")
        emit(response.body<GameDetails>())
    }
}