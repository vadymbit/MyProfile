package com.vadym.myprofile.presentation.model.mapper

import com.vadym.myprofile.domain.model.UserModel
import com.vadym.myprofile.presentation.model.ContactModel

object ContactMapper {
    fun toUserModel(e: ContactModel): UserModel {
        return UserModel(
            e.id,  e.name, e.email, e.phoneNumber, e.career, e.address, e.birthDate, e.urlPhoto
        )
    }

    fun toContactModel(t: UserModel): ContactModel {
        return ContactModel(
            t.id, t.name, t.career, "", "", t.address, t.birthDate, t.urlPhoto
        )
    }
}