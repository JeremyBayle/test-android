package com.baylej.android.test.ui.common

sealed class ViewState<out T> {
    object Loading: ViewState<Nothing>()
    data class SyncedDataLoaded<out T>(val data: T) : ViewState<T>()
    data class CacheDataLoaded<out T>(val data: T): ViewState<T>()
    object NetworkError: ViewState<Nothing>()
    data class HttpError(val code: Int? = null, val message: String? = null): ViewState<Nothing>()
}

fun toViewState(code: Int?, message: String?): ViewState<Nothing> {
    return if (code == -1) {
        ViewState.NetworkError
    } else {
        ViewState.HttpError(code, message)
    }
}