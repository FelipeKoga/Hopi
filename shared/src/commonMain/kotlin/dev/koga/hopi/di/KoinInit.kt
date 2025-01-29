package dev.koga.hopi.di

import dev.koga.hopi.analytics.AnalyticsLogger
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

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

    fun loadNativeModules(analyticsLogger: AnalyticsLogger) {
        koin.loadModules(
            modules = listOf(
                module { single<AnalyticsLogger> { analyticsLogger } }
            )
        )
    }
}
