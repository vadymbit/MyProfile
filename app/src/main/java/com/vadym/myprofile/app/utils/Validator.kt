package com.vadym.myprofile.app.utils

import android.content.Context
import android.util.Patterns
import com.vadym.myprofile.R

object Validator {
    fun validatePassword(pass: String?, context: Context): String? {
        val error: String? = when {
            pass.isNullOrBlank() -> {
                context.getString(R.string.error_required)
            }
            pass.length < Constants.MIN_PASS_LENGTH -> {
                context.getString(R.string.error_short_password, Constants.MIN_PASS_LENGTH)
            }
            pass.length > Constants.MAX_PASS_LENGTH -> {
                context.getString(R.string.error_big_password, Constants.MAX_PASS_LENGTH)
            }
            else -> {
                null
            }
        }
        return error
    }

    fun validatePhoneNumber(phoneNumber: String): Boolean {
        return Patterns.PHONE.matcher(phoneNumber).matches()
    }

    fun validateRequiredField(fieldText: String?): Boolean {
        return fieldText.isNullOrBlank()
    }

    fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}