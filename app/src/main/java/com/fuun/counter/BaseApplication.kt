package com.fuun.counter

import android.app.Application
import com.fuun.counter.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initDi()
        initLogger()
    }

    private fun initDi() {
        startKoin {
            androidContext(this@BaseApplication)
            androidLogger()
            modules(appModule)
        }
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
