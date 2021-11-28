package com.baylej.android.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey val id: String,
    val title: String,
    val firstName: String,
    val lastName: String,
    val picture: String
    )
