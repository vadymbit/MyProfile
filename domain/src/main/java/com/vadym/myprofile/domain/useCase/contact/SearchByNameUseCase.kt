package com.vadym.myprofile.domain.useCase.contact

import com.vadym.myprofile.domain.model.Result
import com.vadym.myprofile.domain.model.UserModel
import com.vadym.myprofile.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchByNameUseCase @Inject constructor(private val contactRepository: ContactRepository) {
    suspend operator fun invoke(contactName: String): Result<Flow<List<UserModel>>, Throwable> {
        return contactRepository.searchContactsByName(contactName = contactName)
    }
}