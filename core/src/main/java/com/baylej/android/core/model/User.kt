package com.baylej.android.core.model

import java.io.Serializable

data class User(
    val id: String,
    val title: String,
    val firstName: String,
    val lastName: String,
    val picture: String) : Serializable