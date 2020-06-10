package com.fuun.counter.resources

import android.content.Context
import com.fuun.counter.R

class CounterResources(
    private val context: Context
) {
    val counterHasNeverStarted = context.getString(R.string.counter_has_never_started)
    fun lastStarted(date: String) = context.getString(R.string.counter_last_started, date)
}
