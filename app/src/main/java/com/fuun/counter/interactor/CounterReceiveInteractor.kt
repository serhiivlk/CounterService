package com.fuun.counter.interactor

import android.content.Context
import com.fuun.counter.receiver.CounterBroadcastReceiver

class CounterReceiveInteractor(
    private val context: Context
) {
    private val receiver = CounterBroadcastReceiver { result ->

    }

    fun register() {

    }

    fun unregister() {

    }
}
