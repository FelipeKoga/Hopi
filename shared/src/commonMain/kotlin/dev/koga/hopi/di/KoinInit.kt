package dev.koga.hopi.di

import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.module.Module

object KoinInit {
    lateinit var koin: Koin

    fun init() {
        koin = startKoin {
            modules(
                appModule,
                viewModelModule
            )
        }.koin
    }
}
