package dev.koga.mmofinder

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import kotlinx.coroutines.flow.flow

private const val BASE_URL = "https://mmo-games.p.rapidapi.com"

data class Filter(
    val platform: Platform,
    val category: String,
    val sortBy: String,
) {
    enum class Platform(val value: String) {
        BROWSER("browser")
    }
}

class MmoRepository(
    private val client: HttpClient
) {

    fun getAll() = flow {
        val response = client.get("$BASE_URL/games") {
            header("x-rapidapi-key", "")
        }
        emit(response.body<List<Game>>())
    }
}