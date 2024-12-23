package dev.koga.mmofinder

import android.app.Application
import dev.koga.mmofinder.feature.home.module

class MMOApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin(module)
    }
}