package com.baylej.android.test.ui.userslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.baylej.android.test.R
import com.baylej.android.test.ui.userdetail.UserDetailActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersListActivity : AppCompatActivity() {

    private val usersListViewModel: UsersListViewModel by viewModel()

    private var adapter: UsersListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        usersListViewModel.usersList.observe(this, Observer { users ->
            adapter?.updateUsers(users) ?: run {
                adapter = UsersListAdapter(this, users)
                users_expendable_list.setAdapter(adapter)
            }

        })
        usersListViewModel.loading.observe(this, Observer { show ->
            if (show) loader.show() else loader.hide()
        })
        usersListViewModel.getUsers()

        users_expendable_list.setOnChildClickListener { _, _, listPosition, expandedListPosition, _ ->
            val user = usersListViewModel.usersList.value?.get(listPosition)?.second?.get(expandedListPosition)
            UserDetailActivity.startActivity(this, user)
            true
        }
    }
}