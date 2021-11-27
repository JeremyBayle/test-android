package com.baylej.android.test.ui.userdetail

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.baylej.android.core.model.User

class UserDetailViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return UserDetailViewModel(defaultArgs?.getSerializable(UserDetailActivity.USER_EXTRA) as User) as T
    }
}