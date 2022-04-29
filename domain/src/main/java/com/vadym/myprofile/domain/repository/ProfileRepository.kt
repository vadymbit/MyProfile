package com.vadym.myprofile.domain.repository

import com.vadym.myprofile.domain.model.Result
import com.vadym.myprofile.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun editUserProfile(userModel: UserModel): Result<Boolean, Throwable>

    suspend fun getUserProfile(): Result<Flow<UserModel>, Throwable>

    fun getProfileId(): Int
}