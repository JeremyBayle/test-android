package com.baylej.android.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.baylej.android.test.ui.UserListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: UserListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loader.show()
        viewModel.usersList.observe(this, Observer {
            Log.i("TEST", "Users list size : " + it.size)
            loader.hide()
        })
        viewModel.getUsers()
    }
}