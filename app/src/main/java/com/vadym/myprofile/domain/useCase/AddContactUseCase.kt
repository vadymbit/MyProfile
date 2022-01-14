package com.vadym.myprofile.domain.useCase

import com.vadym.myprofile.domain.repository.ContactRepository

class AddContactUseCase(private val contactRepository: ContactRepository) {

    operator fun invoke() {
        contactRepository.addContact()
    }

}