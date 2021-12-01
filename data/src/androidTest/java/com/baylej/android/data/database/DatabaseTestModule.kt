package com.baylej.android.data.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.koin.dsl.module

val databaseTestModules = module {
    single {
        Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            UserDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }
    single { get<UserDatabase>().userDao() }
    single { get<UserDatabase>().userDetailDao() }
}