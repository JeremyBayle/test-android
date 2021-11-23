package com.baylej.android.core.usecase

import com.baylej.android.core.repository.UserRepository

class GetUsersUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke() = userRepository.getUsers()
}