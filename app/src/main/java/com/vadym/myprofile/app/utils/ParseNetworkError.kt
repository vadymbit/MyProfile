package com.vadym.myprofile.app.utils

object ParseNetworkError {

    fun parseErrorMessage(errorCode: Int): String {
        return when (errorCode) {
            -1 -> "No internet connection"
            204 -> "Bad server response. Try again later"
            400 -> "User already exists"
            403 -> "Not valid email or password"
            404 -> "User not found"
            else -> "Unknown error"
        }
    }
}