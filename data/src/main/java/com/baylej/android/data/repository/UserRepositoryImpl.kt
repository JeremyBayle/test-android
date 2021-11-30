package com.baylej.android.data.repository

import com.baylej.android.core.model.*
import com.baylej.android.core.repository.UserRepository
import com.baylej.android.data.api.*
import com.baylej.android.data.database.entity.UserDao
import com.baylej.android.data.database.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val apiService: ApiService,
    private val userDao: UserDao) : UserRepository {

    override suspend fun getUsers(): ResultWrapper<List<User>> {
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
                    result
                }
                is ResultWrapper.ErrorResponse, ResultWrapper.NetworkError -> {
                    val users = userDao.getAll().map { elem ->
                        User(
                            elem.id,
                            elem.title,
                            elem.firstName,
                            elem.lastName,
                            elem.picture
                        )
                    }
                    if (users.isNotEmpty()) {
                        ResultWrapper.CacheData(users)
                    } else {
                        result
                    }
                }
                is ResultWrapper.CacheData -> result
            }
        }
    }
}