package com.vadym.myprofile.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUserProfile()

    suspend fun editUserProfile()

    fun getUserEmail(): Flow<String>

}