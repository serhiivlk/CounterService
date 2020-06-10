package com.fuun.counter.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.*

private const val COUNTER_CHANGE_ACTION = "com.fuun.counter.STATE_CHANGE"

private const val KEY_LAST_STARTED = "key_last_started"
private const val KEY_CURRENT_VALUE = "key_current_value"

fun Context.sendCounterStartedBroadcast(date: Date) {
    sendBroadcast(Intent(COUNTER_CHANGE_ACTION).apply {
        putExtra(KEY_LAST_STARTED, date.time)
    })
}

fun Context.sendCounterChangedValueBroadcast(value: Int) {
    sendBroadcast(Intent(COUNTER_CHANGE_ACTION).apply {
        putExtra(KEY_CURRENT_VALUE, value)
    })
}

class CounterBroadcastReceiver(
    private val callback: (result: ReceivedResult) -> Unit
) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.run {
            getLongExtra(KEY_LAST_STARTED, -1).takeIf { it > 0 }?.also {
                callback(ReceivedResult.ChangedStarted(Date(it)))
            }
            getIntExtra(KEY_CURRENT_VALUE, -1).takeIf { it > 0 }?.also {
                callback(ReceivedResult.ChangedValue(it))
            }
        }
    }

    sealed class ReceivedResult {
        data class ChangedStarted(val date: Date) : ReceivedResult()
        data class ChangedValue(val value: Int) : ReceivedResult()
    }
}
