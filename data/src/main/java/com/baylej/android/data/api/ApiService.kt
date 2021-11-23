package com.baylej.android.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users")
    suspend fun getUsersPreview(): Response<List<UserPreviewResponse>>

    @GET("user/{id}")
    suspend fun getUserDetail(@Path("id") id : String): Response<UserDetailResponse>
}