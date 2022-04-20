package com.vadym.myprofile.data.model.mapper

import com.vadym.myprofile.data.model.UserDB
import com.vadym.myprofile.domain.model.UserModel

object UserDomainMapper {
    fun toUserDB(e: UserModel) =
        UserDB(
            e.id,
            e.name,
            e.email,
            e.phoneNumber,
            e.career,
            e.address,
            e.birthDate,
            e.urlPhoto
        )

    fun toUserModel(t: UserDB): UserModel {
        return UserModel(
            t.uid,
            t.name,
            t.email,
            t.phone,
            t.career,
            t.address,
            t.birthday,
            t.image
        )
    }
}