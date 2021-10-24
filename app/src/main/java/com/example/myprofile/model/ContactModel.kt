package com.example.myprofile.model

data class ContactModel(
    val name: String,
    val career: String,
    val phoneNumber: Long,
    val email: String,
    val address: String,
    val birthDate: String,
    val urlPhoto: String?
)
