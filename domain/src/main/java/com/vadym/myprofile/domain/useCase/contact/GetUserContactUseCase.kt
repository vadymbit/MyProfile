package com.vadym.myprofile.domain.useCase.contact

import com.vadym.myprofile.domain.model.Result
import com.vadym.myprofile.domain.model.UserModel
import com.vadym.myprofile.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserContactUseCase @Inject constructor(private val contactRepository: ContactRepository) {
    suspend operator fun invoke(): Result<Flow<List<UserModel>>, Exception> {
        return contactRepository.getUserContacts()
    }
}