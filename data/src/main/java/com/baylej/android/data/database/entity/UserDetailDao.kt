package com.baylej.android.data.database.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDetailDao {

    @Query("SELECT * FROM userdetailentity WHERE userId LIKE :userId")
    fun findByUserId(userId: String): UserDetailEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserDetail(userDetail: UserDetailEntity)
}