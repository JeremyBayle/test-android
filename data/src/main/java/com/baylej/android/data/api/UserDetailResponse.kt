package com.baylej.android.data.api

data class ListResponse<T> (
    val data: List<T>,
    val total: Int,
    val page: Int,
    private val limit: Int
)

data class UserDetailResponse(
    val id: String,
    val title: String,
    val firstName: String,
    val lastName: String,
    val picture: String,
    val gender: String,
    val email: String,
    val dateOfBirth: String,
    val registerDate: String,
    val phone: String,
    val location: LocationResponse
)

data class LocationResponse(
    val street: String,
    val city: String,
    val state: String,
    val country: String,
    val timezone: String
)