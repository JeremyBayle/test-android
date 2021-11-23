package com.baylej.android.core.usecase

import com.baylej.android.core.model.User
import com.baylej.android.core.repository.UserRepository

class AddUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(user: User) = userRepository.addUser(user)
}