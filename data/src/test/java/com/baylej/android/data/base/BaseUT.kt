package com.baylej.android.data.base

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import java.io.File

abstract class BaseUT  : KoinTest {

    private lateinit var mockServerInstance: MockWebServer

    @Before
    open fun setUp(){
        startMockServer()
    }

    fun mockNetworkResponseWithFileContent(fileName: String, responseCode: Int) = mockServerInstance.enqueue(
        MockResponse()
            .setResponseCode(responseCode)
            .setBody(getJson(fileName)))

    private fun getJson(path : String) : String {
        val uri = javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }

    private fun startMockServer(){
        mockServerInstance = MockWebServer()
        mockServerInstance.start()
    }

    fun getMockWebServerUrl() = mockServerInstance.url("/").toString()

    protected fun stopMockServer() {
        mockServerInstance.shutdown()
    }

    @After
    open fun tearDown(){
        stopMockServer()
        stopKoin()
    }
}