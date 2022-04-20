package com.vadym.myprofile.data.source.local

import com.vadym.myprofile.data.model.UserDB
import kotlinx.coroutines.flow.Flow

interface ProfileLocalStorage {

    suspend fun insertUserProfile(profile: UserDB): Boolean

    fun getMyProfile(profileId: Int): Flow<UserDB>
}