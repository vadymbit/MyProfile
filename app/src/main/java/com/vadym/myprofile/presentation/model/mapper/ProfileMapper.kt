package com.vadym.myprofile.presentation.model.mapper

import com.vadym.myprofile.domain.model.UserModel
import com.vadym.myprofile.presentation.model.ProfileModel

object ProfileMapper {
    fun toPresentation(user: UserModel): ProfileModel {
        return ProfileModel(
            user.name,
            user.phoneNumber,
            user.career,
            user.address,
            user.birthDate,
            user.urlPhoto
        )
    }

    fun toDomain(profile: ProfileModel): UserModel {
        return UserModel(
            id = 0,
            name = profile.name,
            email = String(),
            phoneNumber = profile.phoneNumber,
            career = profile.career,
            address = profile.address,
            birthDate = profile.birthDate,
            urlPhoto = profile.urlPhoto
        )
    }
}