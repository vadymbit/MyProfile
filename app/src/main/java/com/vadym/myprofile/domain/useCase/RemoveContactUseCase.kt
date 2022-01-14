package com.vadym.myprofile.domain.useCase

import com.vadym.myprofile.domain.repository.ContactRepository

class RemoveContactUseCase(
    private val contactRepository: ContactRepository
) {
    operator fun invoke() {
        contactRepository.removeContact()
    }
}