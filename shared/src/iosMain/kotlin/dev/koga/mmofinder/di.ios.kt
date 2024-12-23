package dev.koga.mmofinder

import io.ktor.client.engine.cio.CIO
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule: Module = module {
    single { CIO.create() }
}