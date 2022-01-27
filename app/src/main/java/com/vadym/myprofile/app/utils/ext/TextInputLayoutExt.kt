package com.vadym.myprofile.app.utils.ext

import android.util.Patterns
import androidx.core.widget.addTextChangedListener
import com.vadym.myprofile.R
import com.google.android.material.textfield.TextInputLayout
import com.vadym.myprofile.app.utils.Constants.MAX_PASS_LENGTH
import com.vadym.myprofile.app.utils.Constants.MIN_PASS_LENGTH

fun TextInputLayout.addValidatePasswordListener() {
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

fun TextInputLayout.addValidateEmailListener() {
    isErrorEnabled = true
    editText?.addTextChangedListener {
        validateEmail()
    }
}

fun TextInputLayout.validateEmail() {
    if (Patterns.EMAIL_ADDRESS.matcher(editText?.text.toString()).matches()) {
        isErrorEnabled = false
    } else {
        error = context.getString(R.string.error_incorrect_email)
        requestFocus()
    }
}

fun TextInputLayout.validatePhoneNumber() {
    if (Patterns.PHONE.matcher(editText?.text.toString()).matches()) {
        isErrorEnabled = false
    } else {
        error = context.getString(R.string.error_incorrect_phone_number)
        requestFocus()
    }
}

fun TextInputLayout.validateRequiredField() {
    if (editText?.text.isNullOrBlank()) {
        error = context.getString(R.string.error_required)
        requestFocus()
    } else {
        isErrorEnabled = false
    }
}