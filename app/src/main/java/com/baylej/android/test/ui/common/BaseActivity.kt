package com.baylej.android.test.ui.common

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

open class BaseActivity: AppCompatActivity() {

    fun showErrorMessage(message: String) {
        Snackbar.make(users_expendable_list, message, Snackbar.LENGTH_SHORT)
            .show()
    }
}