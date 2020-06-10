package com.fuun.counter.di

import com.fuun.counter.preferences.CounterStore
import com.fuun.counter.preferences.CounterStoreImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    factory<CounterStore> { CounterStoreImpl(androidContext()) }
}
