package com.baylej.android.data.database.entity

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class, UserDetailEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun userDetailDao(): UserDetailDao
}