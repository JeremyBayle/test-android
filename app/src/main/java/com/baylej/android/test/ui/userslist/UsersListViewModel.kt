package com.baylej.android.test.ui.userslist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baylej.android.core.model.ResultWrapper
import com.baylej.android.core.model.User
import com.baylej.android.core.usecase.GetUsersUseCase
import kotlinx.coroutines.launch

class UsersListViewModel(private val getUsersUseCase: GetUsersUseCase) : ViewModel() {

    val usersList: MutableLiveData<List<Pair<Char, List<User>>>> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData()

    fun getUsers() {
        loading.postValue(true)
        viewModelScope.launch {
            when (val result = getUsersUseCase()) {
                is ResultWrapper.NetworkError -> Log.e("TEST", "Network unavailable")
                is ResultWrapper.ErrorResponse-> Log.e("TEST", "Erreur : " + result.message)
                is ResultWrapper.Success -> {
                    usersList.postValue(groupByFirstLetter(result.value).toSortedMap().toList())
                    loading.postValue(false)
                }
            }
        }
    }

    private fun groupByFirstLetter(users: List<User>): Map<Char,List<User>> {
        return users.groupBy { user -> user.lastName.first().uppercaseChar() }
    }
}