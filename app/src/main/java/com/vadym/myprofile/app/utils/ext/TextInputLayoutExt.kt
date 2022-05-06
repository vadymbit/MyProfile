package com.vadym.myprofile.app.utils.ext

import com.google.android.material.textfield.TextInputLayout
import com.vadym.myprofile.R
import com.vadym.myprofile.app.utils.Validator

fun TextInputLayout.removeErrorIfNotFocused() {
    editText?.setOnFocusChangeListener { _, isFocused ->
        if (!isFocused) {
            isErrorEnabled = false
        }
    }
}

fun TextInputLayout.validatePassword() {
    val result = Validator.validatePassword(editText?.text.toString(), context)
    if (result == null) {
        isErrorEnabled = false
    } else {
        error = result
        requestFocus()
    }
}

fun TextInputLayout.validateEmail() {
    if (Validator.validateEmail(editText?.text.toString())) {
        isErrorEnabled = false
    } else {
        error = context.getString(R.string.error_incorrect_email)
        requestFocus()
    }
}

fun TextInputLayout.validatePhoneNumber() {
    if (Validator.validatePhoneNumber(editText?.text.toString())) {
        isErrorEnabled = false
    } else {
        error = context.getString(R.string.error_incorrect_phone_number)
        requestFocus()
    }
}

fun TextInputLayout.validateRequiredField() {
    if (Validator.validateRequiredField(editText?.text.toString())) {
        error = context.getString(R.string.error_required)
        requestFocus()
    } else {
        isErrorEnabled = false
    }
}