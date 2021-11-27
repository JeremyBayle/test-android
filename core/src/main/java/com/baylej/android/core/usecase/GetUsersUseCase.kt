package com.baylej.android.core.usecase

import com.baylej.android.core.model.ResultWrapper
import com.baylej.android.core.model.User

interface GetUsersUseCase {

    suspend operator fun invoke(): ResultWrapper<List<User>>
}