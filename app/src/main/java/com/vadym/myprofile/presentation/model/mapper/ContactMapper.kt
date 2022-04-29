package com.vadym.myprofile.presentation.model.mapper

import com.vadym.myprofile.domain.model.UserModel
import com.vadym.myprofile.presentation.model.ContactModel

object ContactMapper {
    fun toUserModel(contactModel: ContactModel) = contactModel.run {
        UserModel(
            id = id,
            name = name,
            email = email,
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

    fun toContactModel(t: UserModel) = t.run {
        ContactModel(
            id = id,
            name = name,
            career = career,
            phoneNumber = phoneNumber,
            email = email,
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