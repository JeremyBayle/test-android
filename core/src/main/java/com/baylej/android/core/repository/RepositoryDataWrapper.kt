package com.baylej.android.core.repository

sealed class RepositoryDataWrapper<out T> {
    data class SyncedData<out T>(val value: T): RepositoryDataWrapper<T>()
    data class CacheData<out T>(val value: T): RepositoryDataWrapper<T>()
    data class Error(val code: Int? = null, val message: String? = null): RepositoryDataWrapper<Nothing>()
}
