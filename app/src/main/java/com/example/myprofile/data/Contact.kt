package com.example.myprofile.data

import android.net.Uri
import com.example.myprofile.R

data class Contact(
    val name: String,
    val career: String,
    val phoneNumber: Long,
    val email: String,
    val address: String,
    val birthDate: String,
    var urlPhoto: String = Uri.parse((R.drawable.ic_launcher_background).toString()).toString()
)
