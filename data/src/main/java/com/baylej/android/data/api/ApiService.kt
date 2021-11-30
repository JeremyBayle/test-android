package com.baylej.android.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("user")
    suspend fun getUsersPreview(@Query("limit") limit: Int): ListResponse<UserPreviewResponse>

    @GET("user/{id}")
    suspend fun getUserDetail(@Path("id") id : String): UserDetailResponse
}