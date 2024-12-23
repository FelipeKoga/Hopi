package dev.koga.mmofinder

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

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
            install(ContentNegotiation) {
                json(Json { isLenient = true; ignoreUnknownKeys = true })
            }
        }
    }
    singleOf(::MmoRepository)
    includes(platformModule)
}

expect val platformModule: Module