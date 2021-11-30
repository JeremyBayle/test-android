package com.baylej.android.data.di

import com.baylej.android.core.repository.UserDetailsRepository
import com.baylej.android.core.repository.UserRepository
import com.baylej.android.data.repository.UserDetailsRepositoryImpl
import com.baylej.android.data.repository.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModules = module {
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
    single<UserDetailsRepository> { UserDetailsRepositoryImpl(get(), get()) }
}