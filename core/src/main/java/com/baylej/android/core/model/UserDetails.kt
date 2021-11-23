package com.baylej.android.core.model

import java.util.*

data class UserDetails(
    val gender:String,
    val email: String,
    val dateOfBirth: Date,
    val registerDate: String,
    val phone: String,
    val location: Location
)

data class Location(
    val street: String,
    val city: String,
    val state: String,
    val country: String,
    val timezone: String
    )