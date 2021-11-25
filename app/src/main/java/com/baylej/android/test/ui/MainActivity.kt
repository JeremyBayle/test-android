package com.baylej.android.test.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.baylej.android.test.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: UserListViewModel by viewModels()

    private var adapter: UsersListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.usersList.observe(this, Observer {users ->
            adapter?.updateUsers(users) ?: run {
                adapter = UsersListAdapter(this, users)
                users_list.setAdapter(adapter)
            }

        })
        viewModel.loading.observe(this, Observer { show ->
            if (show) loader.show() else loader.hide()
        })
        viewModel.getUsers()
    }
}