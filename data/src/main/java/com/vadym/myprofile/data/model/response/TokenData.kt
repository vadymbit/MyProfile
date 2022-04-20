package com.vadym.myprofile.data.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TokenData(
    val accessToken: String,
    val refreshToken: String
)