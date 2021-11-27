package com.baylej.android.core.model


data class UserDetails(
    val gender:String,
    val email: String,
    val dateOfBirth: String,
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