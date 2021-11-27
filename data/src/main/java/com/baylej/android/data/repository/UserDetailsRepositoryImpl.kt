package com.baylej.android.data.repository

import com.baylej.android.core.model.Location
import com.baylej.android.core.model.ResultWrapper
import com.baylej.android.core.model.User
import com.baylej.android.core.model.UserDetails
import com.baylej.android.core.repository.UserDetailsRepository
import com.baylej.android.data.api.ApiService
import com.baylej.android.data.api.apiCall
import kotlinx.coroutines.Dispatchers

class UserDetailsRepositoryImpl(private val apiService: ApiService): UserDetailsRepository {
    override suspend fun getUserDetails(id: String): ResultWrapper<UserDetails> {
        return apiCall(Dispatchers.IO) {
            val response = apiService.getUserDetail(id)
            UserDetails(
                response.gender,
                response.email,
                response.dateOfBirth,
                response.registerDate,
                response.phone,
                Location(
                    response.location.street,
                    response.location.city,
                    response.location.state,
                    response.location.country,
                    response.location.timezone
                )
            )
        }
    }

    override suspend fun addUserDetails(user: User, userDetails: UserDetails) {
        TODO("Not yet implemented")
    }
}