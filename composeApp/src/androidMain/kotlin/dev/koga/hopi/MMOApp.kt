package dev.koga.hopi

import android.app.Application
import dev.koga.hopi.feature.home.module

class MMOApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin(module)
    }
}