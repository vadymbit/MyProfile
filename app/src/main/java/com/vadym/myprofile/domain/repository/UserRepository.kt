package com.vadym.myprofile.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserEmail(): Flow<String>
}