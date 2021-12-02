package com.baylej.android.test.ui.userslist

import android.os.Bundle
import androidx.lifecycle.Observer
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
        usersListViewModel.viewState.observe(this, Observer {
            when(it) {
                is ViewState.Loading -> loader.show()
                is ViewState.NetworkError -> showErrorMessage("Network unavailable, please retry later")
                is ViewState.HttpError -> showErrorMessage(it.message + " / " + it.code)
                is ViewState.SyncedDataLoaded -> {
                    configureUsersList(it.data)
                }
                is ViewState.CacheDataLoaded -> {
                    configureUsersList(it.data)
                    showErrorMessage("Failed to synchronize data, cache data is displayed")
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