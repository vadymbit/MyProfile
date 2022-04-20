package com.vadym.myprofile.domain.useCase.auth

import com.vadym.myprofile.domain.model.AuthModel
import com.vadym.myprofile.domain.model.Result
import com.vadym.myprofile.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(email: String, password: String): Result<Boolean, Exception> {
        val authModel = AuthModel(email, password)
        return authRepository.register(authModel)
    }
}