package com.baylej.android.data.repository

import com.baylej.android.core.repository.RepositoryDataWrapper
import com.baylej.android.core.repository.UserDetailsRepository
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
class UserDetailRepositoryUT: BaseUT() {

    private val userDetailRepository: UserDetailsRepository by inject()
    private val userId = "60d0fe4f5311236168a109ca"

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
        mockNetworkResponseWithFileContent("success_response_detail.json", HttpURLConnection.HTTP_OK)
        val repoData = userDetailRepository.getUserDetails(userId)

        Assert.assertNotNull(repoData)
        Assert.assertEquals(true, repoData is RepositoryDataWrapper.SyncedData)
        Assert.assertEquals("sara.andersen@example.com", (repoData as RepositoryDataWrapper.SyncedData).value.email)
        Assert.assertEquals("92694011", repoData.value.phone)
        Assert.assertEquals("Denmark", repoData.value.location.country)
    }

    @Test
    fun `test user repository no connection error`()  =  runBlocking {
        stopMockServer()
        val repoData = userDetailRepository.getUserDetails(userId)

        Assert.assertNotNull(repoData)
        Assert.assertEquals(true, repoData is RepositoryDataWrapper.Error)
        Assert.assertEquals(-1, (repoData as RepositoryDataWrapper.Error).code)
    }

    @Test
    fun `test user repository http error`()  =  runBlocking {
        mockNetworkResponseWithFileContent("error_invalid_app_id.json", HttpURLConnection.HTTP_FORBIDDEN)
        val repoData = userDetailRepository.getUserDetails(userId)

        Assert.assertNotNull(repoData)
        Assert.assertEquals(true, repoData is RepositoryDataWrapper.Error)
        Assert.assertEquals(403, (repoData as RepositoryDataWrapper.Error).code)
    }
}