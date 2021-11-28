package com.baylej.android.data.di

import okhttp3.mockwebserver.MockWebServer
import org.koin.dsl.module

val mockWebServerModuleTest = module {
    factory { MockWebServer() }
}
