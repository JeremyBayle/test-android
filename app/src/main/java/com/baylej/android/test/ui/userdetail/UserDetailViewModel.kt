package com.baylej.android.test.ui.userdetail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baylej.android.core.model.ResultWrapper
import com.baylej.android.core.model.User
import com.baylej.android.core.model.UserDetails
import com.baylej.android.core.usecase.GetUserDetailUseCase
import com.baylej.android.core.usecase.GetUserDetailUseCaseImpl
import com.baylej.android.data.api.ApiClient
import com.baylej.android.data.repository.UserDetailsRepositoryImpl
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val user: User?,
    private val getUserDetailUseCase: GetUserDetailUseCase): ViewModel() {

    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val userDetail: MutableLiveData<UserDetails> = MutableLiveData()

    fun getUserDetail() {
        loading.postValue(true)
        viewModelScope.launch {
            user?.let {
                when (val result = getUserDetailUseCase(it.id)) {
                    is ResultWrapper.NetworkError -> Log.e("TEST", "Network unavailable")
                    is ResultWrapper.ErrorResponse-> Log.e("TEST", "Erreur : " + result.message)
                    is ResultWrapper.Success -> {
                        userDetail.postValue(result.value)
                        loading.postValue(false)
                    }
                    is ResultWrapper.CacheData -> {
                        userDetail.postValue(result.value)
                        loading.postValue(false)
                    }
                }
            } ?: run {
                Log.e("TEST", "Erreur")
            }
        }
    }

    fun userPicture() = user?.picture
    fun userFirstName() = user?.firstName
    fun userLastName() = user?.lastName
    fun userEmail() = userDetail.value?.email
    fun userPhoneNumber() = userDetail.value?.phone
}