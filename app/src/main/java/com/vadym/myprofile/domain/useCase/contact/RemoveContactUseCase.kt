package com.vadym.myprofile.domain.useCase.contact

import com.vadym.myprofile.domain.model.ContactModel
import com.vadym.myprofile.domain.repository.ContactRepository
import javax.inject.Inject

class RemoveContactUseCase @Inject constructor(
    private val contactRepository: ContactRepository
) {
    suspend operator fun invoke(contact: ContactModel) {
        contactRepository.removeContact(contact)
    }
}