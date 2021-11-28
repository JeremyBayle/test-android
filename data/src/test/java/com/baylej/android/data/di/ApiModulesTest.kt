package com.baylej.android.data.di

import com.baylej.android.data.api.ApiClient
import org.koin.dsl.module

fun apiModulesTest(baseUrl: String) = module {
    single { ApiClient(baseUrl) }
    single { get<ApiClient>().apiService}
}