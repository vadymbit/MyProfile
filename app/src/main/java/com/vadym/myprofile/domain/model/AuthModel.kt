package com.vadym.myprofile.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthModel(
    val email: String,
    val password: String
) : Parcelable
