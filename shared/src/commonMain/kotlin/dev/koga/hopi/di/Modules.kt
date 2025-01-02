package dev.koga.hopi.di

import dev.koga.hopi.repository.GameRepository
import dev.koga.hopi.shared.BuildKonfig
import dev.koga.hopi.viewmodel.CategoryGamesViewModel
import dev.koga.hopi.viewmodel.GameDetailsViewModel
import dev.koga.hopi.viewmodel.GamesViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

const val BASE_URL = "https://mmo-games.p.rapidapi.com"

val viewModelModule = module {
    viewModelOf(::GamesViewModel)
    viewModelOf(::GameDetailsViewModel)
    viewModel { params ->
        CategoryGamesViewModel(params.get(), get())
    }
}

val appModule = module {
    single {
        HttpClient(engine = get()) {
            defaultRequest {
                header("x-rapidapi-key", BuildKonfig.RAPID_API_KEY)
                header("x-rapidapi-host", "mmo-games.p.rapidapi.com")
            }

            install(ContentNegotiation) {
                json(Json { isLenient = true; ignoreUnknownKeys = true })
            }
        }
    }
    singleOf(::GameRepository)
    includes(platformModule)
}

expect val platformModule: Module