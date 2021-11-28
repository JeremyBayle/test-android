package com.baylej.android.data.api

import com.baylej.android.data.base.BaseUT
import com.baylej.android.data.di.apiModulesTest
import com.baylej.android.data.di.mockWebServerModuleTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.test.inject
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class ApiServiceUT: BaseUT() {

    private val apiService : ApiService by inject()

    @Before
    fun start(){
        super.setUp()
        startKoin{ modules(mockWebServerModuleTest + apiModulesTest(getMockWebServerUrl()))}
    }

    @Test
    fun `test get users api client success response`() =  runBlocking {
        mockNetworkResponseWithFileContent("success_response_list.json", HttpURLConnection.HTTP_OK)
        val dataReceived = apiService.getUsersPreview(3)

        Assert.assertNotNull(dataReceived)
        Assert.assertEquals(3, dataReceived.data.count())
        Assert.assertEquals(3, dataReceived.limit)
        Assert.assertEquals(99, dataReceived.total)
    }

    @Test
    fun `test get user detail api client response`() =  runBlocking {
        mockNetworkResponseWithFileContent("success_response_detail.json", HttpURLConnection.HTTP_OK)

        val dataReceived = apiService.getUserDetail("60d0fe4f5311236168a109ca")

        Assert.assertNotNull(dataReceived)
        Assert.assertEquals("sara.andersen@example.com", dataReceived.email)
        Assert.assertEquals("92694011", dataReceived.phone)
        Assert.assertEquals("Denmark", dataReceived.location.country)
    }
}