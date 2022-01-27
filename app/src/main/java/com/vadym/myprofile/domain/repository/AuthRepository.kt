package com.vadym.myprofile.domain.repository

import com.vadym.myprofile.domain.model.AuthModel
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun register(authModel: AuthModel): Boolean

    suspend fun login(): Boolean

    suspend fun refreshToken()

    suspend fun rememberUser(isLogged: Boolean, email: String)

    fun isLogged(): Flow<Boolean>
}