package dev.koga.hopi

import android.app.Application
import dev.koga.hopi.di.viewModelModule

class HopiApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin(viewModelModule)
    }
}