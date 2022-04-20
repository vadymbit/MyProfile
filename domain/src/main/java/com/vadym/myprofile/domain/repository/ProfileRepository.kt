package com.vadym.myprofile.domain.repository

import com.vadym.myprofile.domain.model.Result
import com.vadym.myprofile.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun editUserProfile(userModel: UserModel): Result<Boolean, Exception>

    suspend fun getUserProfile(): Result<Flow<UserModel>, Exception>

    fun getProfileId(): Int
}