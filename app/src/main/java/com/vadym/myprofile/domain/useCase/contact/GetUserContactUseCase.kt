package com.vadym.myprofile.domain.useCase.contact

import com.vadym.myprofile.domain.model.ContactModel
import com.vadym.myprofile.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserContactUseCase @Inject constructor(private val contactRepository: ContactRepository) {
    suspend operator fun invoke(): Flow<List<ContactModel>> {
        return contactRepository.getUserContacts()
    }
}