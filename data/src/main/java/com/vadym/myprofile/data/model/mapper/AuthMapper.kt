package com.vadym.myprofile.data.model.mapper

import com.vadym.myprofile.data.model.request.AuthRequest
import com.vadym.myprofile.domain.model.AuthModel

object AuthMapper {
    fun toAuthRequest(authModel: AuthModel) = AuthRequest(
        email = authModel.email,
        password = authModel.password
    )
}