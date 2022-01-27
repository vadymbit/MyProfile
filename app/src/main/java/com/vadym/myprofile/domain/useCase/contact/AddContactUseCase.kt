package com.vadym.myprofile.domain.useCase.contact

import com.vadym.myprofile.domain.repository.ContactRepository

class AddContactUseCase(private val contactRepository: ContactRepository) {

    suspend operator fun invoke() {
        contactRepository.addContact()
    }

}