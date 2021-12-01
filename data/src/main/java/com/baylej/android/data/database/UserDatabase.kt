package com.baylej.android.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.baylej.android.data.database.dao.UserDao
import com.baylej.android.data.database.dao.UserDetailDao
import com.baylej.android.data.database.entity.UserDetailEntity
import com.baylej.android.data.database.entity.UserEntity

@Database(entities = [UserEntity::class, UserDetailEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun userDetailDao(): UserDetailDao
}