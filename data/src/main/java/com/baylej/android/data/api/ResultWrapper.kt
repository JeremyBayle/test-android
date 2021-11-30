package com.baylej.android.data.api

import com.baylej.android.core.repository.RepositoryDataWrapper

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class ErrorResponse(val code: Int? = null, val message: String? = null): ResultWrapper<Nothing>()
    object NetworkError: ResultWrapper<Nothing>()
}

fun <T> ResultWrapper.Success<T>.toSyncedRepositoryData() =
    RepositoryDataWrapper.SyncedData(this.value)

fun ResultWrapper.ErrorResponse.toErrorRepositoryData() =
    RepositoryDataWrapper.Error(this.code, this.message)

fun toErrorRepositoryData() =
    RepositoryDataWrapper.Error(-1, "No connection error")