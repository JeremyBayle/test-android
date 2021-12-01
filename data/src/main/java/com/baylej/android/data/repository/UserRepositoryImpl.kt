package com.baylej.android.data.repository

import com.baylej.android.core.model.*
import com.baylej.android.core.repository.RepositoryDataWrapper
import com.baylej.android.core.repository.UserRepository
import com.baylej.android.data.api.*
import com.baylej.android.data.database.dao.UserDao
import com.baylej.android.data.database.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val apiService: ApiService,
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getUsers(): RepositoryDataWrapper<List<User>> {
        return withContext(Dispatchers.IO) {
            val result = apiCall(Dispatchers.IO) {
                apiService.getUsersPreview(100).data.map { elem ->
                    User(
                        elem.id,
                        elem.title,
                        elem.firstName,
                        elem.lastName,
                        elem.picture
                    )
                }
            }
            when (result) {
                is ResultWrapper.Success -> {
                    userDao.insertUsers(result.value.map { elem ->
                        UserEntity(
                            elem.id,
                            elem.title,
                            elem.firstName,
                            elem.lastName,
                            elem.picture
                        )
                    })
                    result.toSyncedRepositoryData()
                }
                is ResultWrapper.ErrorResponse, ResultWrapper.NetworkError -> {
                    val users = findInLocalDatabase()
                    if (users.isNotEmpty()) {
                        RepositoryDataWrapper.CacheData(users)
                    } else {
                        if (result is ResultWrapper.ErrorResponse) {
                            result.toErrorRepositoryData()
                        } else {
                            toErrorRepositoryData()
                        }
                    }
                }
            }
        }
    }

    private fun findInLocalDatabase(): List<User> = userDao.getAll().map { elem ->
        User(
            elem.id,
            elem.title,
            elem.firstName,
            elem.lastName,
            elem.picture
        )
    }
}