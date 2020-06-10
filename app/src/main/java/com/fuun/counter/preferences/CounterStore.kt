package com.fuun.counter.preferences

import android.content.Context
import androidx.core.content.edit
import java.util.*

interface CounterStore {
    val lastServiceStarted: Date?
    fun setLastServiceStarted(date: Date)
    var currentCounterValue: Int
}

class CounterStoreImpl(context: Context) : CounterStore {
    private val prefs = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)

    override val lastServiceStarted: Date?
        get() = prefs.getLong(PREF_KEY_LAST_STARTED, -1)
            .takeIf { it > 0 }
            ?.let(::Date)

    override fun setLastServiceStarted(date: Date) {
        prefs.edit {
            putLong(PREF_KEY_LAST_STARTED, date.time)
        }
    }

    override var currentCounterValue: Int
        get() = prefs.getInt(PREF_KEY_CURRENT_VALUE, 0)
        set(value) {
            prefs.edit {
                putInt(PREF_KEY_CURRENT_VALUE, value)
            }
        }

    companion object {
        private const val PREFS_FILE = "counter_preferences"

        private const val PREF_KEY_LAST_STARTED = "last_service_started"
        private const val PREF_KEY_CURRENT_VALUE = "current_value"
    }
}
