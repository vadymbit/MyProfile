package com.vadym.myprofile.presentation.model.mapper

import com.vadym.myprofile.domain.model.UserModel
import com.vadym.myprofile.presentation.model.ProfileModel

object ProfileMapper {
    fun toPresentation(user: UserModel) = user.run {
        ProfileModel(
            name = name,
            phoneNumber = phoneNumber,
            career = career,
            address = address,
            birthDate = birthDate,
            urlPhoto = urlPhoto,
            facebook = facebook,
            instagram = instagram,
            twitter = twitter,
            linkedin = linkedin
        )
    }

    fun toDomain(profile: ProfileModel) = profile.run {
        UserModel(
            id = 0,
            name = name,
            email = "",
            phoneNumber = phoneNumber,
            career = career,
            address = address,
            birthDate = birthDate,
            urlPhoto = urlPhoto,
            facebook = facebook,
            instagram = instagram,
            twitter = twitter,
            linkedin = linkedin
        )
    }
}