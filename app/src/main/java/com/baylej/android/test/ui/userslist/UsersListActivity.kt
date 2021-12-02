package com.baylej.android.test.ui.userslist

import android.os.Bundle
import com.baylej.android.core.model.User
import com.baylej.android.test.R
import com.baylej.android.test.ui.common.BaseActivity
import com.baylej.android.test.ui.common.ViewState
import com.baylej.android.test.ui.userdetail.UserDetailActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersListActivity : BaseActivity() {

    private val usersListViewModel: UsersListViewModel by viewModel()
    private var adapter: UsersListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        usersListViewModel.viewState.observe(this, {
            when(it) {
                is ViewState.Loading -> loader.show()
                is ViewState.NetworkError -> showErrorMessage(getString(R.string.network_error), users_expendable_list)
                is ViewState.HttpError -> showErrorMessage(getString(R.string.http_error,
                    it.message + " / " + it.code), users_expendable_list)
                is ViewState.SyncedDataLoaded -> {
                    configureUsersList(it.data)
                }
                is ViewState.CacheDataLoaded -> {
                    configureUsersList(it.data)
                    showErrorMessage(getString(R.string.synchronization_error), users_expendable_list)
                }
            }
            if (it !is ViewState.Loading) {
                loader.hide()
            }
        })
        usersListViewModel.getUsers()

        users_expendable_list.setOnChildClickListener { _, _, listPosition, expandedListPosition, _ ->
            val user = usersListViewModel.usersList[listPosition].second[expandedListPosition]
            UserDetailActivity.startActivity(this, user)
            true
        }
    }

    private fun configureUsersList(users: List<Pair<Char, List<User>>>) {
        adapter?.updateUsers(users) ?: run {
            adapter = UsersListAdapter(this, users)
            users_expendable_list.setAdapter(adapter)
        }
    }
}