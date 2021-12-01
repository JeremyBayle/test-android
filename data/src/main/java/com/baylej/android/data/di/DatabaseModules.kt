package com.baylej.android.data.di

import androidx.room.Room
import com.baylej.android.data.database.UserDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModules = module {
    single { Room.databaseBuilder(androidApplication(), UserDatabase::class.java, "user-database")
        //.fallbackToDestructiveMigration()
        .build()
    }
    single { get<UserDatabase>().userDao() }
    single { get<UserDatabase>().userDetailDao() }

}