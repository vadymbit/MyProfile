package com.vadym.myprofile.data.model.mapper

import com.vadym.myprofile.data.model.UserDB
import com.vadym.myprofile.data.model.UserDTO
import com.vadym.myprofile.domain.model.UserModel

object UserDataMapper{
    fun toUserDB(t: UserDTO) =
        UserDB(
            t.id,
            t.name.orEmpty(),
            t.email,
            t.phone.orEmpty(),
            t.career.orEmpty(),
            t.address.orEmpty(),
            t.birthday.orEmpty(),
            t.image.orEmpty()
        )

    fun toUserModel(userDTO: UserDTO) = userDTO.run {
        UserModel(
            id = id,
            name = name.orEmpty(),
            email = email,
            phoneNumber = phone.orEmpty(),
            career = career.orEmpty(),
            address = address.orEmpty(),
            birthDate = birthday.orEmpty(),
            urlPhoto = image.orEmpty()
        )
    }
}