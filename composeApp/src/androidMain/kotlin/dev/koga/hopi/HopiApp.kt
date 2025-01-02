package dev.koga.hopi

import android.app.Application
import dev.koga.hopi.di.KoinInit

class HopiApp : Application() {

    override fun onCreate() {
        super.onCreate()

        KoinInit.init()
    }
}