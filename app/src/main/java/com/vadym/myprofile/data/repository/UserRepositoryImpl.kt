package com.vadym.myprofile.data.repository

import com.vadym.myprofile.data.storage.LocalPrefsStore
import com.vadym.myprofile.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val storage: LocalPrefsStore) :
    UserRepository {

    override suspend fun getUserProfile() {
        TODO("Not yet implemented")
    }

    override suspend fun editUserProfile() {
        TODO("Not yet implemented")
    }

    override fun getUserEmail(): Flow<String> {
        return storage.getUserEmail()
    }
}