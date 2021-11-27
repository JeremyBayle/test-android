package com.baylej.android.core.di

import com.baylej.android.core.usecase.GetUserDetailUseCase
import com.baylej.android.core.usecase.GetUserDetailUseCaseImpl
import com.baylej.android.core.usecase.GetUsersUseCase
import com.baylej.android.core.usecase.GetUsersUseCaseImpl
import org.koin.dsl.module

val useCaseModules = module {
    factory<GetUsersUseCase > { GetUsersUseCaseImpl(get()) }
    factory<GetUserDetailUseCase> { GetUserDetailUseCaseImpl(get())}
}