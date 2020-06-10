package com.fuun.counter.service

import android.content.Context
import android.content.Intent

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
