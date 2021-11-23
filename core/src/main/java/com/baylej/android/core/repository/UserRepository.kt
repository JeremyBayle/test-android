package com.baylej.android.core.repository

import com.baylej.android.core.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUsers(): Flow<List<User>>

    suspend fun addUser(user: User)
}