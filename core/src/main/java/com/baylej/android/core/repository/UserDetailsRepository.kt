package com.baylej.android.core.repository

import com.baylej.android.core.model.UserDetails

interface UserDetailsRepository {

    suspend fun getUserDetails(id: String): RepositoryDataWrapper<UserDetails>
}