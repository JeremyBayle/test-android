package com.baylej.android.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDetailEntity(
    @PrimaryKey val userId: String,
    val gender:String,
    val email: String,
    val dateOfBirth: String,
    val registerDate: String,
    val phone: String,
    @Embedded val location: LocationEntity
)

data class LocationEntity(
    val street: String,
    val city: String,
    val state: String,
    val country: String,
    val timezone: String
)
