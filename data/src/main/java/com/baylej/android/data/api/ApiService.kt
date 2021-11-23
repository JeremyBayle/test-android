package com.baylej.android.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("user")
    suspend fun getUsersPreview(@Query("limit") limit: Int): Response<List<UserPreviewResponse>>

    @GET("user/{id}")
    suspend fun getUserDetail(@Path("id") id : String): Response<UserDetailResponse>
}