package com.fuun.counter.di

import com.fuun.counter.interactor.CounterReceiveInteractor
import com.fuun.counter.interactor.CounterServiceInteractor
import com.fuun.counter.preferences.CounterStore
import com.fuun.counter.preferences.CounterStoreImpl
import com.fuun.counter.resources.CounterResources
import com.fuun.counter.ui.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory { CounterResources(androidContext()) }
    factory<CounterStore> { CounterStoreImpl(androidContext()) }
    factory { CounterReceiveInteractor(androidContext(), get()) }
    factory { CounterServiceInteractor(androidContext()) }
    viewModel { MainViewModel(get(), get(), get()) }
}
