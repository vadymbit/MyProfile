package com.vadym.myprofile.data.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ServerResponse<Data>(
    val code: Int,
    val data: Data,
    val message: String,
    val status: String
)
