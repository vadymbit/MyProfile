package com.vadym.myprofile.data.model.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthRequest(
    val email: String,
    val password: String,
)
