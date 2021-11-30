package com.baylej.android.core.repository

import com.baylej.android.core.model.ResultWrapper
import com.baylej.android.core.model.User
import com.baylej.android.core.model.UserDetails

interface UserDetailsRepository {

    suspend fun getUserDetails(id: String): ResultWrapper<UserDetails>
}