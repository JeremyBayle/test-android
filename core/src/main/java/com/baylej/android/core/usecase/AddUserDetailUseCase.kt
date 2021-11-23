package com.baylej.android.core.usecase

import com.baylej.android.core.model.User
import com.baylej.android.core.model.UserDetails
import com.baylej.android.core.repository.UserDetailsRepository

class AddUserDetailUseCase(private val userDetailsRepository: UserDetailsRepository) {
    suspend operator fun invoke(user:User, userDetails: UserDetails) = userDetailsRepository.addUserDetails(user, userDetails)
}