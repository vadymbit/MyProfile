package com.vadym.myprofile.data.source.local

import com.vadym.myprofile.data.model.UserDB
import com.vadym.myprofile.data.source.local.db.UsersDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileLocalStorageImpl @Inject constructor(private val db: UsersDao) : ProfileLocalStorage {
    override suspend fun insertUserProfile(profile: UserDB): Boolean {
        db.insertProfile(profile)
        return true
    }

    override fun getMyProfile(profileId: Int): Flow<UserDB> {
        return db.getProfile(profileId)
    }
}