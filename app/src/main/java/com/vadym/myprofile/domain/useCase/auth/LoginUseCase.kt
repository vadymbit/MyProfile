package com.vadym.myprofile.domain.useCase.auth

import com.vadym.myprofile.domain.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke() {
        authRepository.login()
    }
}