package com.vadym.myprofile.domain.useCase

import com.vadym.myprofile.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserEmailUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(): Flow<String> {
        return userRepository.getUserEmail()
    }
}