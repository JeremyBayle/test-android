package com.baylej.android.test.ui.userdetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import com.baylej.android.core.model.User
import com.baylej.android.core.model.UserDetails
import com.baylej.android.test.R
import com.baylej.android.test.ui.common.BaseActivity
import com.baylej.android.test.ui.common.ViewState
import com.baylej.android.test.ui.utils.CircleTransformation
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class UserDetailActivity : BaseActivity() {

    private val userDetailViewModel: UserDetailViewModel by viewModel {
        parametersOf(intent.getSerializableExtra(USER_EXTRA))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        userDetailViewModel.viewState.observe(this) {
            when(it) {
                is ViewState.NetworkError -> showErrorMessage("Network unavailable, please retry later")
                is ViewState.HttpError -> showErrorMessage(it.message + " / " + it.code)
                is ViewState.SyncedDataLoaded -> {
                    updateUI(it.data.first, it.data.second)
                }
                is ViewState.CacheDataLoaded -> {
                    updateUI(it.data.first, it.data.second)
                    showErrorMessage("Failed to synchronize data, cache data is displayed")
                }
                ViewState.Loading -> loader.show()
            }
            if (it !is ViewState.Loading) {
                loader.hide()
            }
        }
        user_email_layout.setEndIconOnClickListener {
            sendEmail()
        }
        user_phone_layout.setEndIconOnClickListener {
            call()
        }
        userDetailViewModel.getUserDetail()
    }

    private fun updateUI(user: User, userDetails: UserDetails){
        Picasso.get()
            .load(user.picture)
            .fit()
            .centerCrop()
            .transform(CircleTransformation())
            .into(user_picture)
        user_first_name.setText(user.firstName,TextView.BufferType.NORMAL)
        user_last_name.setText(user.lastName,TextView.BufferType.NORMAL)
        user_email.setText(userDetails.email, TextView.BufferType.NORMAL)
        user_phone.setText(userDetails.phone, TextView.BufferType.NORMAL)
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
        fun startActivity(context: Context, user: User){
            val intent = Intent(context, UserDetailActivity::class.java)
            intent.putExtra(USER_EXTRA, user)
            context.startActivity(intent)
        }
    }
}