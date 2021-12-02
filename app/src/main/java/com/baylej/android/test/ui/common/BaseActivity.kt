package com.baylej.android.test.ui.common

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

open class BaseActivity: AppCompatActivity() {

    fun showErrorMessage(message: String, view: View) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            .show()
    }
}