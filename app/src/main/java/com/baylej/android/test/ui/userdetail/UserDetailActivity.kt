package com.baylej.android.test.ui.userdetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import com.baylej.android.core.model.User
import com.baylej.android.test.R
import com.baylej.android.test.ui.utils.CircleTransformation
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_detail.*

class UserDetailActivity : AppCompatActivity() {

    private val viewModel: UserDetailViewModel by viewModels {
        UserDetailViewModelFactory(this, intent.extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        viewModel.userDetail.observe(this) {
            Picasso.get()
                    .load(viewModel.userPicture())
                    .fit()
                    .centerCrop()
                    .transform(CircleTransformation())
                    .into(user_picture)
            user_first_name.setText(viewModel.userFirstName(),TextView.BufferType.NORMAL)
            user_last_name.setText(viewModel.userLastName(),TextView.BufferType.NORMAL)
            user_email.setText(viewModel.userEmail(), TextView.BufferType.NORMAL)
            user_phone.setText(viewModel.userPhoneNumber(), TextView.BufferType.NORMAL)
        }
        user_email_layout.setEndIconOnClickListener {
            sendEmail()
        }
        user_phone_layout.setEndIconOnClickListener {
            call()
        }
        viewModel.getUserDetail()
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, viewModel.userEmail())
        }
        startActivity(intent)
    }

    private fun call() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:" + viewModel.userPhoneNumber())
        startActivity(intent)
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