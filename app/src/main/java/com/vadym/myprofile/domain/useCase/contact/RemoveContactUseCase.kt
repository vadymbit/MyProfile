package com.vadym.myprofile.domain.useCase.contact

import com.vadym.myprofile.domain.repository.ContactRepository

class RemoveContactUseCase(
    private val contactRepository: ContactRepository
) {
    suspend operator fun invoke() {
        contactRepository.removeContact()
    }
}