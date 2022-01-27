package com.vadym.myprofile.presentation.ui.contacts.addContactDialog

import androidx.lifecycle.ViewModel

class ContactAddViewModel : ViewModel() {

    fun isInputValid(
        addressErrorEnabled: Boolean,
        userNameErrorEnabled: Boolean,
        emailErrorEnabled: Boolean,
        phoneErrorEnabled: Boolean
    ): Boolean {
        return !addressErrorEnabled && !userNameErrorEnabled && !emailErrorEnabled && !phoneErrorEnabled
    }
}