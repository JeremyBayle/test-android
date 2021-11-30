package com.baylej.android.core.usecase

import com.baylej.android.core.model.User
import com.baylej.android.core.repository.RepositoryDataWrapper

interface GetUsersUseCase {

    suspend operator fun invoke(): RepositoryDataWrapper<List<User>>
}