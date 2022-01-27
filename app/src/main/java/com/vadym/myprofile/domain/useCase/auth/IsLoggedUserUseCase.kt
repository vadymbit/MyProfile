package com.vadym.myprofile.domain.useCase.auth

import com.vadym.myprofile.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsLoggedUserUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(): Flow<Boolean> {
        return authRepository.isLogged()
    }
}