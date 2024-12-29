package dev.koga.hopi

import dev.koga.hopi.repository.GameRepository
import dev.koga.hopi.shared.BuildKonfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

const val BASE_URL = "https://mmo-games.p.rapidapi.com"

fun initKoin(module: Module) {
    startKoin {
        modules(
            module,
            appModule
        )
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