package com.vadym.myprofile.domain.model

data class UserModel(
    val id: Int,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val career: String,
    val address: String,
    val birthDate: String,
    val urlPhoto: String,
    val facebook: String,
    val instagram: String,
    val twitter: String,
    val linkedin: String
)
