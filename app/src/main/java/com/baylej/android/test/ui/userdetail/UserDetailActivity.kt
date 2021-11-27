package com.baylej.android.test.ui.userdetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.baylej.android.core.model.User
import com.baylej.android.test.R
import com.baylej.android.test.ui.utils.CircleTransformation
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class UserDetailActivity : AppCompatActivity() {

    private val userDetailViewModel: UserDetailViewModel by viewModel {
        parametersOf(intent.getSerializableExtra(USER_EXTRA))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        userDetailViewModel.userDetail.observe(this) {
            Picasso.get()
                    .load(userDetailViewModel.userPicture())
                    .fit()
                    .centerCrop()
                    .transform(CircleTransformation())
                    .into(user_picture)
            user_first_name.setText(userDetailViewModel.userFirstName(),TextView.BufferType.NORMAL)
            user_last_name.setText(userDetailViewModel.userLastName(),TextView.BufferType.NORMAL)
            user_email.setText(userDetailViewModel.userEmail(), TextView.BufferType.NORMAL)
            user_phone.setText(userDetailViewModel.userPhoneNumber(), TextView.BufferType.NORMAL)
        }
        user_email_layout.setEndIconOnClickListener {
            sendEmail()
        }
        user_phone_layout.setEndIconOnClickListener {
            call()
        }
        userDetailViewModel.getUserDetail()
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, userDetailViewModel.userEmail())
        }
        startActivity(intent)
    }

    private fun call() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:" + userDetailViewModel.userPhoneNumber())
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