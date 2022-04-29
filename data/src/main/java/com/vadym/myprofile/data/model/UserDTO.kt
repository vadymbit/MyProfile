package com.vadym.myprofile.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDTO(
    val email: String,
    val name: String?,
    val phone: String?,
    val address: String?,
    val career: String?,
    val birthday: String?,
    val image: String?,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "created_at")
    val createdAt: String,
    val id: Int,
    val facebook: String?,
    val instagram: String?,
    val twitter: String?,
    val linkedin: String?
)
