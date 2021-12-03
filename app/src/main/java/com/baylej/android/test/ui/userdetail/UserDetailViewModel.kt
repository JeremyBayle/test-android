package com.baylej.android.test.ui.userdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baylej.android.core.model.User
import com.baylej.android.core.model.UserDetails
import com.baylej.android.core.repository.RepositoryDataWrapper
import com.baylej.android.core.usecase.GetUserDetailUseCase
import com.baylej.android.test.ui.common.ViewState
import com.baylej.android.test.ui.common.toViewState
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val user: User,
    private val getUserDetailUseCase: GetUserDetailUseCase): ViewModel() {

    private var userDetails: UserDetails? = null

    private val mutableViewState = MutableLiveData<ViewState<Pair<User,UserDetails>>>()
    val viewState: LiveData<ViewState<Pair<User,UserDetails>>> = mutableViewState

    fun getUserDetail() {
        mutableViewState.postValue(ViewState.Loading)
        viewModelScope.launch {
            when (val result = getUserDetailUseCase(user.id)) {
                is RepositoryDataWrapper.Error -> {
                        mutableViewState.postValue(toViewState(result.code, result.message))
                }
                is RepositoryDataWrapper.SyncedData -> {
                    userDetails = result.value
                    mutableViewState.postValue(ViewState.SyncedDataLoaded(Pair(user, result.value)))
                }
                is RepositoryDataWrapper.CacheData -> {
                    userDetails = result.value
                    mutableViewState.postValue(ViewState.CacheDataLoaded(Pair(user,result.value)))
                }
            }
        }
    }

    fun userEmail() = userDetails?.email
    fun userPhoneNumber() = userDetails?.phone
}