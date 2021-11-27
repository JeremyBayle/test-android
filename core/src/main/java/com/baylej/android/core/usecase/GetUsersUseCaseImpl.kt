package com.baylej.android.core.usecase

import com.baylej.android.core.repository.UserRepository

class GetUsersUseCaseImpl(private val userRepository: UserRepository): GetUsersUseCase {
    override suspend operator fun invoke() = userRepository.getUsers()
}