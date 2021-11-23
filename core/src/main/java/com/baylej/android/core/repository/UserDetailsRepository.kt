package com.baylej.android.core.repository

import com.baylej.android.core.model.User
import com.baylej.android.core.model.UserDetails
import kotlinx.coroutines.flow.Flow

interface UserDetailsRepository {

    suspend fun getUserDetails(): Flow<UserDetails>

    suspend fun addUserDetails(user: User, userDetails: UserDetails)
}