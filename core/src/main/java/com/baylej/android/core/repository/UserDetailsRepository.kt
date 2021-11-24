package com.baylej.android.core.repository

import com.baylej.android.core.model.User
import com.baylej.android.core.model.UserDetails

interface UserDetailsRepository {

    suspend fun getUserDetails(): Result<UserDetails>

    suspend fun addUserDetails(user: User, userDetails: UserDetails)
}