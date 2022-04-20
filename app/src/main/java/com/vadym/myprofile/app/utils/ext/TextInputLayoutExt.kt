package com.vadym.myprofile.app.utils.ext

import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout
import com.vadym.myprofile.R
import com.vadym.myprofile.app.utils.Validator

fun TextInputLayout.addValidatePasswordListener(): TextWatcher? {
    isErrorEnabled = true
    return editText?.addTextChangedListener {
        error = Validator.validatePassword(it.toString(), context)
        isErrorEnabled = !error.isNullOrBlank()
    }
}

fun TextInputLayout.addValidateEmailListener(): TextWatcher? {
    isErrorEnabled = true
    return editText?.addTextChangedListener {
        validateEmail()
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