package com.fuun.counter.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.fuun.counter.preferences.CounterStore
import com.fuun.counter.receiver.sendCounterChangedValueBroadcast
import com.fuun.counter.receiver.sendCounterStartedBroadcast
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import java.util.*
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

class CounterService : Service() {
    private val store: CounterStore by inject()

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    private var counterJob: Job? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (counterJob?.isActive != true) {
            counterJob = startCounter()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()

        job.cancel()
    }

    @OptIn(ExperimentalTime::class)
    private fun startCounter() =
        scope.launch {
            Date().also {
                store.setLastServiceStarted(it)
                sendCounterStartedBroadcast(it)
            }

            var currentValue = store.currentCounterValue

            while (isActive) {
                delay(COUNTER_DELAY)
                currentValue += 1

                store.currentCounterValue = currentValue
                sendCounterChangedValueBroadcast(currentValue)
            }
        }

    companion object {
        @OptIn(ExperimentalTime::class)
        private val COUNTER_DELAY = 5.seconds
    }
}
