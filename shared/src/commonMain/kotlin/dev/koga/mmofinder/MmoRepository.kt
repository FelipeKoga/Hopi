package dev.koga.mmofinder

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import kotlinx.coroutines.flow.flow


class MmoRepository(
    private val client: HttpClient
) {

    fun getAll() = flow {
        val response = client.get("$BASE_URL/games")
        emit(response.body<List<Game>>())
    }

    fun getById(id: Int) = flow {
        val response = client.get("$BASE_URL/game?id=${id}")
        emit(response.body<Game>())
    }
}