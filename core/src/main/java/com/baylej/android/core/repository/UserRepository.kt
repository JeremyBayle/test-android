package com.baylej.android.core.repository

import com.baylej.android.core.model.Result
import com.baylej.android.core.model.User

interface UserRepository {

    suspend fun getUsers(): Result<List<User>>

    suspend fun addUser(user: User)
}