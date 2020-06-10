package com.fuun.counter.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CounterService : Service() {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    private var counterJob: Job? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()

        job.cancel()
    }

    private fun startCounter() {
        if (counterJob?.isActive != true) {
            counterJob = scope.launch {

            }
        }
    }
}
