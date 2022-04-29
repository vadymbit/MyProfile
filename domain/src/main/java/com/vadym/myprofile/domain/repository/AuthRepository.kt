package com.vadym.myprofile.domain.repository

import com.vadym.myprofile.domain.model.AuthModel
import com.vadym.myprofile.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun register(authModel: AuthModel): Result<Boolean, Throwable>

    suspend fun login(authModel: AuthModel): Result<Boolean, Throwable>

    suspend fun logout()

    suspend fun rememberUser()

    fun isLogged(): Flow<Boolean>
}