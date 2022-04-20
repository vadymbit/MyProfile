package com.vadym.myprofile.domain.useCase.profile

import com.vadym.myprofile.domain.repository.ProfileRepository
import javax.inject.Inject

class GetProfileIdUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    operator fun invoke(): Int {
        return profileRepository.getProfileId()
    }
}