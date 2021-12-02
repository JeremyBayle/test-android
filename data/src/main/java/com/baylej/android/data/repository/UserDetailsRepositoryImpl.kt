package com.baylej.android.data.repository

import com.baylej.android.core.model.Location
import com.baylej.android.core.model.UserDetails
import com.baylej.android.core.repository.RepositoryDataWrapper
import com.baylej.android.core.repository.UserDetailsRepository
import com.baylej.android.data.api.*
import com.baylej.android.data.database.dao.UserDetailDao
import com.baylej.android.data.database.entity.LocationEntity
import com.baylej.android.data.database.entity.UserDetailEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserDetailsRepositoryImpl(
    private val apiService: ApiService,
    private val userDetailDao: UserDetailDao
): UserDetailsRepository {

    override suspend fun getUserDetails(id: String): RepositoryDataWrapper<UserDetails> {
        return withContext(Dispatchers.IO) {
            val result = apiCall(Dispatchers.IO) {
                val response = apiService.getUserDetail(id)
                UserDetails(
                    response.gender,
                    response.email,
                    response.dateOfBirth,
                    response.registerDate,
                    response.phone,
                    Location(
                        response.location.street,
                        response.location.city,
                        response.location.state,
                        response.location.country,
                        response.location.timezone
                    )
                )
            }
            when (result) {
                is ResultWrapper.Success -> {
                    result.value.apply {
                        userDetailDao.insertUserDetail(
                            UserDetailEntity(
                                id,
                                this.gender,
                                this.email,
                                this.dateOfBirth,
                                this.registerDate,
                                this.phone,
                                LocationEntity(
                                    this.location.street,
                                    this.location.city,
                                    this.location.state,
                                    this.location.country,
                                    this.location.timezone
                                )
                            )
                        )
                    }
                    result.toSyncedRepositoryData()
                }
                is ResultWrapper.ErrorResponse, ResultWrapper.NetworkError -> {
                    userDetailDao.findByUserId(id)?.let {
                        RepositoryDataWrapper.CacheData(UserDetails(
                            it.gender,
                            it.email,
                            it.dateOfBirth,
                            it.registerDate,
                            it.phone,
                            Location(
                                it.location.street,
                                it.location.city,
                                it.location.state,
                                it.location.country,
                                it.location.timezone
                            )
                        ))
                    } ?: kotlin.run {
                        if (result is ResultWrapper.ErrorResponse) {
                            result.toErrorRepositoryData()
                        } else {
                            toErrorRepositoryData()
                        }
                    }
                }
            }
        }
    }
}