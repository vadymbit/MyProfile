package com.vadym.myprofile.data.model.response

import com.squareup.moshi.JsonClass
import com.vadym.myprofile.data.model.UserDTO

@JsonClass(generateAdapter = true)
data class UserData(
    val user: UserDTO
)