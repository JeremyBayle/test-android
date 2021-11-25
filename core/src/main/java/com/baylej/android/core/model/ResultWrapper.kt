package com.baylej.android.core.model


sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class ErrorResponse(val code: Int? = null, val message: String? = null): ResultWrapper<Nothing>()
    object NetworkError: ResultWrapper<Nothing>()
}