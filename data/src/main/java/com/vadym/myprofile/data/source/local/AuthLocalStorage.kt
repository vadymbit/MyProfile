package com.vadym.myprofile.data.source.local

import kotlinx.coroutines.flow.Flow

interface AuthLocalStorage {
    suspend fun saveProfileData(
        userAccessToken: String,
        userRefreshToken: String,
        id: Int? = null
    ): Boolean

    fun getAccessToken(): String?

    fun getRefreshToken(): String?

    fun getProfileId(): Int

    suspend fun rememberUser()

    suspend fun clearProfileData(): Boolean

    fun isUserLogged(): Flow<Boolean>
}