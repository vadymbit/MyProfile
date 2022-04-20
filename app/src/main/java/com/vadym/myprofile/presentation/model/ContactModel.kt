package com.vadym.myprofile.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactModel(
    val id: Int,
    val name: String,
    val career: String,
    val phoneNumber: String,
    val email: String,
    val address: String,
    val birthDate: String,
    val urlPhoto: String
) : Parcelable
