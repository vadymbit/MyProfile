package com.vadym.myprofile.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserDB(
    @PrimaryKey val uid: Int,
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val career: String = "",
    val address: String = "",
    val birthday: String = "",
    val image: String = ""
)
