package com.vadym.myprofile.domain.useCase.contact

import com.vadym.myprofile.domain.model.Result
import com.vadym.myprofile.domain.model.UserModel
import com.vadym.myprofile.domain.repository.ContactRepository
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(private val contactRepository: ContactRepository) {
    suspend operator fun invoke(): Result<List<UserModel>, Exception> {
        return contactRepository.getAllUsers()
    }
}