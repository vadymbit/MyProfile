package com.vadym.myprofile.domain.useCase.profile

import com.vadym.myprofile.domain.model.UserModel
import com.vadym.myprofile.domain.repository.ProfileRepository
import javax.inject.Inject

class EditProfileUseCase @Inject constructor(
    private val userRepository: ProfileRepository
) {
    suspend operator fun invoke(user: UserModel) {
        userRepository.editUserProfile(user)
    }
}