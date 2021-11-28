package com.baylej.android.data.repository

import com.baylej.android.core.model.ResultWrapper
import com.baylej.android.core.repository.UserRepository
import com.baylej.android.data.base.BaseUT
import com.baylej.android.data.di.apiModulesTest
import com.baylej.android.data.di.mockWebServerModuleTest
import com.baylej.android.data.di.repositoryModules
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
class UserRepositoryUT : BaseUT() {

    private val userRepository: UserRepository by inject()

    @Before
    fun start(){
        super.setUp()
        startKoin{ modules(
            mockWebServerModuleTest +
                    apiModulesTest(getMockWebServerUrl()) +
                    repositoryModules
        )}
    }

    @Test
    fun `test user repository success response`()  =  runBlocking {
        mockNetworkResponseWithFileContent("success_response_list.json", HttpURLConnection.HTTP_OK)
        val repoData = userRepository.getUsers()

        Assert.assertNotNull(repoData)
        Assert.assertEquals(true, repoData is ResultWrapper.Success)
        Assert.assertEquals(3, (repoData as ResultWrapper.Success).value.count())
    }

    @Test
    fun `test user repository no connection error`()  =  runBlocking {
        stopMockServer()
        val repoData = userRepository.getUsers()

        Assert.assertNotNull(repoData)
        Assert.assertEquals(true, repoData is ResultWrapper.NetworkError)
    }

    @Test
    fun `test user repository http error`()  =  runBlocking {
        mockNetworkResponseWithFileContent("error_invalid_app_id.json", HttpURLConnection.HTTP_FORBIDDEN)
        val repoData = userRepository.getUsers()

        Assert.assertNotNull(repoData)
        Assert.assertEquals(true, repoData is ResultWrapper.ErrorResponse)
        Assert.assertEquals(403, (repoData as ResultWrapper.ErrorResponse).code)
    }
}