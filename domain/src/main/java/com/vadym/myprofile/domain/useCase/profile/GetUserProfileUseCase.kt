package com.vadym.myprofile.domain.useCase.profile

import com.vadym.myprofile.domain.model.Result
import com.vadym.myprofile.domain.model.UserModel
import com.vadym.myprofile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(private val userRepository: ProfileRepository) {
    suspend operator fun invoke(): Result<Flow<UserModel>, Throwable> {
        return userRepository.getUserProfile()
    }
}