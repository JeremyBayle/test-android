package com.baylej.android.core.repository

import com.baylej.android.core.model.ResultWrapper
import com.baylej.android.core.model.User

interface UserRepository {

    suspend fun getUsers(): ResultWrapper<List<User>>

    suspend fun addUser(user: User)
}