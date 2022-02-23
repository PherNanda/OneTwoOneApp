package com.android.app.onetwoone

import android.app.Application
import com.android.app.onetwoone.di.*
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class OTwoOApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Fresco.initialize(this)

        startKoin {
            androidContext(this@OTwoOApplication)
            modules(listOf(repositoriesModule, viewModelModule, useCasesModule, sharedPreferences))
        }
    }
}