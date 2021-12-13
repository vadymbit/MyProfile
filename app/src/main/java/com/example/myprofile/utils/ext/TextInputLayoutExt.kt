package com.example.myprofile.utils.ext

import android.util.Patterns
import androidx.core.widget.addTextChangedListener
import com.example.myprofile.R
import com.example.myprofile.utils.MAX_PASS_LENGTH
import com.example.myprofile.utils.MIN_PASS_LENGTH
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.validatePassword() {
    isErrorEnabled = true
    editText?.addTextChangedListener {
        when {
            it.isNullOrBlank() -> {
                error = context.getString(R.string.error_required)
                requestFocus()
            }
            it.length < MIN_PASS_LENGTH -> {
                error = context.getString(R.string.error_short_password, MIN_PASS_LENGTH)
                requestFocus()
            }
            it.length > MAX_PASS_LENGTH -> {
                boxStrokeErrorColor
                error = context.getString(R.string.error_big_password, MAX_PASS_LENGTH)
                requestFocus()
            }
            else -> {
                isErrorEnabled = false
            }
        }
    }
}

fun TextInputLayout.validateEmail() {
    isErrorEnabled = true
    editText?.addTextChangedListener {
        if (Patterns.EMAIL_ADDRESS.matcher(it.toString()).matches()) {
            isErrorEnabled = false
        } else {
            error = context.getString(R.string.error_incorrect_email)
            requestFocus()
        }
    }
}

fun TextInputLayout.validatePhoneNumber() {
    isErrorEnabled = true
    editText?.addTextChangedListener {
        if (Patterns.PHONE.matcher(it.toString()).matches()) {
            isErrorEnabled = false
        } else {
            error = context.getString(R.string.error_incorrect_phone_number)
            requestFocus()
        }
    }
}

fun TextInputLayout.validateRequiredField() {
    isErrorEnabled = true
    editText?.addTextChangedListener {
        if (it.isNullOrBlank()) {
            error = context.getString(R.string.error_required)
            requestFocus()
        } else {
            isErrorEnabled = false
        }
    }
}