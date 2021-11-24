package com.baylej.android.data.repository

import com.baylej.android.core.model.*
import com.baylej.android.core.repository.UserRepository
import com.baylej.android.data.api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(private val apiService: ApiService) : UserRepository {
    override suspend fun getUsers(): Result<List<User>> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getUsersPreview(100)
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Success(body.map { elem ->
                        User(
                            elem.id,
                            elem.title,
                            elem.firstName,
                            elem.lastName,
                            elem.picture
                        )
                    })
                }
                val emptyList: List<User> = listOf()
                Success(emptyList)
            } else {
                Failure(HttpError(response.message(), response.code()))
            }
        }
    }

    override suspend fun addUser(user: User) {
        TODO("Not yet implemented")
    }
}