package com.vadym.myprofile.presentation.ui.contacts.dialog

import com.vadym.myprofile.app.base.BaseViewModel
import com.vadym.myprofile.domain.model.ContactModel
import com.vadym.myprofile.domain.useCase.contact.AddContactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactAddViewModel @Inject constructor(private val addContactUseCase: AddContactUseCase) :
    BaseViewModel() {

    fun isInputValid(
        addressErrorEnabled: Boolean,
        userNameErrorEnabled: Boolean,
        emailErrorEnabled: Boolean,
        phoneErrorEnabled: Boolean
    ): Boolean {
        return !addressErrorEnabled && !userNameErrorEnabled && !emailErrorEnabled && !phoneErrorEnabled
    }

    fun saveNewContact(
        id: Long,
        username: String,
        career: String?,
        phone: Long,
        email: String,
        address: String?,
        birthDate: String?,
        contactPhoto: String?
    ) {
        val contact = ContactModel(
            id,
            username,
            career,
            phone,
            email,
            address,
            birthDate,
            contactPhoto
        )
        launch {
            addContactUseCase(contact)
        }
    }
}