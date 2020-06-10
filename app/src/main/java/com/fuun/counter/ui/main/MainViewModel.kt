package com.fuun.counter.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuun.counter.interactor.CounterReceiveInteractor
import com.fuun.counter.interactor.CounterReceiveInteractor.ReceivedResult
import com.fuun.counter.interactor.CounterServiceInteractor
import com.fuun.counter.resources.CounterResources
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(
    private val serviceInteractor: CounterServiceInteractor,
    private val receiveInteractor: CounterReceiveInteractor,
    private val resources: CounterResources
) : ViewModel() {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _state = MutableStateFlow(UiState())
    val state: Flow<UiState>
        get() = _state

    init {
        viewModelScope.launch {
            receiveInteractor()
                .collect { handleReceivedResult(it) }
        }
        receiveInteractor.register()
    }

    private fun handleReceivedResult(result: ReceivedResult) {
        when (result) {
            is ReceivedResult.Initial -> updateState {
                copy(
                    lastStarted = result.date.toFormattedState(),
                    currentValue = result.value.toString()
                )
            }
            is ReceivedResult.ReceivedStarted -> updateState {
                copy(lastStarted = result.date.toFormattedState())
            }
            is ReceivedResult.ReceivedValue -> updateState {
                copy(currentValue = result.value.toString())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        receiveInteractor.unregister()
        serviceInteractor.stop()
    }

    fun startService() {
        serviceInteractor.start()
    }

    fun stopService() {
        serviceInteractor.stop()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun updateState(block: UiState.() -> UiState) {
        _state.value = block(_state.value)
    }

    private fun Date?.toFormattedState(): String = when (this) {
        null -> resources.counterHasNeverStarted
        else -> {
            val dateFormatted = LAST_STARTED_FORMATTER.format(this)
            resources.lastStarted(dateFormatted)
        }
    }

    data class UiState(
        val lastStarted: String = "",
        val currentValue: String = ""
    )

    companion object {
        private val LAST_STARTED_FORMATTER = SimpleDateFormat.getDateTimeInstance()
    }
}
