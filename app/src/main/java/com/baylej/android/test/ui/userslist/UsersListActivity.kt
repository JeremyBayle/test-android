package com.baylej.android.test.ui.userslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.baylej.android.test.R
import com.baylej.android.test.ui.userdetail.UserDetailActivity
import kotlinx.android.synthetic.main.activity_main.*

class UsersListActivity : AppCompatActivity() {

    private val viewModel: UsersListViewModel by viewModels()

    private var adapter: UsersListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.usersList.observe(this, Observer {users ->
            adapter?.updateUsers(users) ?: run {
                adapter = UsersListAdapter(this, users)
                users_expendable_list.setAdapter(adapter)
            }

        })
        viewModel.loading.observe(this, Observer { show ->
            if (show) loader.show() else loader.hide()
        })
        viewModel.getUsers()

        users_expendable_list.setOnChildClickListener { _, _, listPosition, expandedListPosition, _ ->
            val user = viewModel.usersList.value?.get(listPosition)?.second?.get(expandedListPosition)
            UserDetailActivity.startActivity(this, user)
            true
        }
    }
}