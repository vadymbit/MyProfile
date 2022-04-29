package com.vadym.myprofile.data.model.mapper

import com.vadym.myprofile.data.model.UserDB
import com.vadym.myprofile.domain.model.UserModel

object UserDomainMapper {
    fun toUserDB(userModel: UserModel) = userModel.run {
        UserDB(
            uid = id,
            name = name,
            email = email,
            phone = phoneNumber,
            career = career,
            address = address,
            birthday = birthDate,
            image = urlPhoto,
            facebook = facebook,
            instagram = instagram,
            twitter = twitter,
            linkedin = linkedin
        )
    }


    fun toUserModel(userDB: UserDB) = userDB.run {
        UserModel(
            id = uid,
            name = name,
            email = email,
            phoneNumber = phone,
            career = career,
            address = address,
            birthDate = birthday,
            urlPhoto = image,
            facebook = facebook,
            instagram = instagram,
            twitter = twitter,
            linkedin = linkedin
        )
    }
}