package com.baylej.android.data.api

data class ListResponse<T> (
    val data: List<T>,
    val total: Int,
    val page: Int,
    val limit: Int
)

data class UserPreviewResponse(
    val id: String,
    val title: String,
    val firstName: String,
    val lastName: String,
    val picture: String
)
