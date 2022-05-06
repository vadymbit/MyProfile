package com.vadym.myprofile.domain.useCase.auth

import com.vadym.myprofile.domain.model.Result
import com.vadym.myprofile.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): Result<Boolean, Throwable> {
        return authRepository.logout()
    }
}