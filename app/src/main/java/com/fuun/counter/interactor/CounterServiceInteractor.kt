package com.fuun.counter.interactor

import android.content.Context
import android.content.Intent
import com.fuun.counter.service.CounterService

class CounterServiceInteractor(private val context: Context) {
    private val serviceIntent
        get() = Intent(context, CounterService::class.java)

    fun start() {
        context.startService(serviceIntent)
    }

    fun stop() {
        context.stopService(serviceIntent)
    }
}
