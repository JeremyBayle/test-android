package com.baylej.android.core.usecase

import com.baylej.android.core.repository.UserDetailsRepository

class GetUserDetailUseCase(private val userDetailsRepository: UserDetailsRepository) {

    suspend operator fun invoke() = userDetailsRepository.getUserDetails()
}