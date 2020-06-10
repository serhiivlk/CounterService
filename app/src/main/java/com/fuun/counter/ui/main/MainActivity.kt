package com.fuun.counter.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.fuun.counter.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {
            actionStart.setOnClickListener { viewModel.startService() }
            actionStop.setOnClickListener { viewModel.stopService() }
        }

        lifecycleScope.launch { viewModel.state.collect { renter(it) } }
    }

    private fun renter(state: MainViewModel.UiState) {
        binding.run {
            lastStarted.text = state.lastStarted
            counterValue.text = state.currentValue
        }
    }
}
