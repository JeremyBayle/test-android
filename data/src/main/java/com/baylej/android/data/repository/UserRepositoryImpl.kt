package com.baylej.android.data.repository

import com.baylej.android.core.model.*
import com.baylej.android.core.repository.UserRepository
import com.baylej.android.data.api.*
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import java.io.IOException

class UserRepositoryImpl(private val apiService: ApiService) : UserRepository {
    override suspend fun getUsers(): ResultWrapper<List<User>> {
        return safeApiCall(Dispatchers.IO) {
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
    }

    override suspend fun addUser(user: User) {
        TODO("Not yet implemented")
    }
}