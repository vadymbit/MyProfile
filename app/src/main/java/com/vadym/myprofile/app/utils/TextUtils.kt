package com.vadym.myprofile.app.utils

object TextUtils {
    fun parseNameFormEmail(email: String): String {
        val name: String = email.substringBeforeLast("@")
        val firstName = name.substringBefore(".").replaceFirstChar { it.uppercase() }
        val lastName = name.substringAfter(".").replaceFirstChar { it.uppercase() }
        return "$firstName $lastName"
    }
}