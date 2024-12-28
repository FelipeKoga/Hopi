package dev.koga.hopi

import io.ktor.client.engine.js.Js
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module  {
    single { Js.create() }
}