package com.vadym.myprofile.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactModel(
    val id: Long,
    val name: String,
    val career: String?,
    val phoneNumber: Long,
    val email: String,
    val address: String?,
    val birthDate: String?,
    val urlPhoto: String?
) : Parcelable
