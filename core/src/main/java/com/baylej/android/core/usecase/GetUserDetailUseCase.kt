package com.baylej.android.core.usecase

import com.baylej.android.core.model.UserDetails
import com.baylej.android.core.repository.RepositoryDataWrapper

interface GetUserDetailUseCase {

    suspend operator fun invoke(id: String): RepositoryDataWrapper<UserDetails>
}