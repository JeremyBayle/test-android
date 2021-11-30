package com.baylej.android.core.repository

import com.baylej.android.core.model.User

interface UserRepository {
    suspend fun getUsers(): RepositoryDataWrapper<List<User>>
}