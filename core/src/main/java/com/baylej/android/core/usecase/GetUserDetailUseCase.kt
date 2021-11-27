package com.baylej.android.core.usecase

import com.baylej.android.core.model.ResultWrapper
import com.baylej.android.core.model.UserDetails

interface GetUserDetailUseCase {

    suspend operator fun invoke(id: String): ResultWrapper<UserDetails>
}