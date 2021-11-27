package com.baylej.android.test.ui.userdetail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.baylej.android.core.model.User
import com.baylej.android.test.R

class UserDetailActivity : AppCompatActivity() {

    private val viewModel: UserDetailViewModel by viewModels {
        UserDetailViewModelFactory(this, intent.extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        viewModel.getUserDetail()
    }

    companion object {
        const val USER_EXTRA: String = "user_extra"

        fun startActivity(context: Context, user: User?){
            val intent = Intent(context, UserDetailActivity::class.java)
            user?.let {
                intent.putExtra(USER_EXTRA, user)
            }
            context.startActivity(intent)
        }
    }
}