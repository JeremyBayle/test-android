package com.baylej.android.test.ui.userslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baylej.android.core.model.User
import com.baylej.android.core.repository.RepositoryDataWrapper
import com.baylej.android.core.usecase.GetUsersUseCase
import com.baylej.android.test.ui.common.ViewState
import com.baylej.android.test.ui.common.toViewState
import kotlinx.coroutines.launch

class UsersListViewModel(private val getUsersUseCase: GetUsersUseCase) : ViewModel() {

    var usersList:List<Pair<Char, List<User>>> = listOf()
        private set
    private val mutableViewState = MutableLiveData<ViewState<List<Pair<Char, List<User>>>>>()
    val viewState: LiveData<ViewState<List<Pair<Char, List<User>>>>> = mutableViewState

    fun getUsers() {
        mutableViewState.postValue(ViewState.Loading)
        viewModelScope.launch {
            when (val result = getUsersUseCase()) {
                is RepositoryDataWrapper.Error -> {
                    result.toViewState(result.code, result.message)
                }
                is RepositoryDataWrapper.SyncedData -> {
                    usersList = groupByFirstLetter(result.value).toSortedMap().toList()
                    mutableViewState.postValue(ViewState.SyncedDataLoaded(usersList))
                }
                is RepositoryDataWrapper.CacheData -> {
                    usersList = groupByFirstLetter(result.value).toSortedMap().toList()
                    mutableViewState.postValue(ViewState.CacheDataLoaded(usersList))
                }
            }
        }
    }

    private fun groupByFirstLetter(users: List<User>): Map<Char,List<User>> {
        return users.groupBy { user -> user.lastName.first().uppercaseChar() }
    }
}