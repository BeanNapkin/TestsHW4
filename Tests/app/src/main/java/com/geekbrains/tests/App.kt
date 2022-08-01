package com.geekbrains.tests

import android.app.Application
import com.geekbrains.tests.repository.Di
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(Di.mainModule)
        }
    }
}