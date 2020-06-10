package com.fuun.counter.interactor

import android.content.Context
import com.fuun.counter.preferences.CounterStore
import com.fuun.counter.receiver.CounterBroadcastReceiver
import com.fuun.counter.receiver.registerCounterReceiver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*

class CounterReceiveInteractor(
    private val context: Context,
    private val store: CounterStore
) {
    private val receiver = CounterBroadcastReceiver { result ->
        @OptIn(ExperimentalCoroutinesApi::class)
        receivedResult.value = when (result) {
            is CounterBroadcastReceiver.ReceivedResult.ChangedStarted ->
                ReceivedResult.ReceivedStarted(result.date)
            is CounterBroadcastReceiver.ReceivedResult.ChangedValue ->
                ReceivedResult.ReceivedValue(result.value)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val receivedResult = MutableStateFlow<ReceivedResult>(
        ReceivedResult.Initial(
            date = store.lastServiceStarted,
            value = store.currentCounterValue
        )
    )

    operator fun invoke(): Flow<ReceivedResult> {
        return receivedResult
    }

    fun register() {
        context.registerCounterReceiver(receiver)
    }

    fun unregister() {
        context.unregisterReceiver(receiver)
    }

    sealed class ReceivedResult {
        data class Initial(val date: Date?, val value: Int) : ReceivedResult()
        data class ReceivedStarted(val date: Date) : ReceivedResult()
        data class ReceivedValue(val value: Int) : ReceivedResult()
    }
}
