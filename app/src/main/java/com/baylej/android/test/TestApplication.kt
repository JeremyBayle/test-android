package com.baylej.android.test

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.baylej.android.core.di.useCaseModules
import com.baylej.android.core.model.User
import com.baylej.android.data.di.apiModules
import com.baylej.android.data.di.databaseModules
import com.baylej.android.data.di.repositoryModules
import com.baylej.android.test.ui.userdetail.UserDetailViewModel
import com.baylej.android.test.ui.userslist.UsersListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class TestApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@TestApplication)
            modules(appModule +
                    apiModules +
                    repositoryModules +
                    useCaseModules +
                    databaseModules)
        }
    }
}

val appModule = module {
    viewModel { UsersListViewModel(get()) }
    viewModel { (user: User) ->
        UserDetailViewModel(user, get())
    }
}