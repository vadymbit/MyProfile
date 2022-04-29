package com.vadym.myprofile.data.model.mapper

import com.vadym.myprofile.data.model.UserDB
import com.vadym.myprofile.data.model.UserDTO
import com.vadym.myprofile.domain.model.UserModel

object UserDTOMapper{
    fun toUserDB(userDTO: UserDTO) = userDTO.run {
        UserDB(
            uid = id,
            name = name.orEmpty(),
            email = email,
            phone = phone.orEmpty(),
            career = career.orEmpty(),
            address = address.orEmpty(),
            birthday = birthday.orEmpty(),
            image = image.orEmpty(),
            facebook = facebook.orEmpty(),
            instagram = instagram.orEmpty(),
            twitter = twitter.orEmpty(),
            linkedin = linkedin.orEmpty()
        )
    }

    fun toUserModel(userDTO: UserDTO) = userDTO.run {
        UserModel(
            id = id,
            name = name.orEmpty(),
            email = email,
            phoneNumber = phone.orEmpty(),
            career = career.orEmpty(),
            address = address.orEmpty(),
            birthDate = birthday.orEmpty(),
            urlPhoto = image.orEmpty(),
            facebook = facebook.orEmpty(),
            instagram = instagram.orEmpty(),
            twitter = twitter.orEmpty(),
            linkedin = linkedin.orEmpty()
        )
    }
}