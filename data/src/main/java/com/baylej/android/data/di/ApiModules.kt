package com.baylej.android.data.di

import com.baylej.android.data.api.ApiClient
import org.koin.dsl.module

val apiModules = module {
    single { ApiClient() }
    single { get<ApiClient>().apiService}
}