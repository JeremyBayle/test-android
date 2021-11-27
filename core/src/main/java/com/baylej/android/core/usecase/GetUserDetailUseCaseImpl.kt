package com.baylej.android.core.usecase

import com.baylej.android.core.repository.UserDetailsRepository

class GetUserDetailUseCaseImpl(private val userDetailsRepository: UserDetailsRepository): GetUserDetailUseCase {

    override suspend operator fun invoke(id: String) = userDetailsRepository.getUserDetails(id)
}