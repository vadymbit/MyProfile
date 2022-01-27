package com.vadym.myprofile.data.repository

import com.vadym.myprofile.data.storage.LocalPrefsStore
import com.vadym.myprofile.domain.model.AuthModel
import com.vadym.myprofile.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(val storage: LocalPrefsStore) : AuthRepository {

    override suspend fun login(): Boolean {
        return true
    }

    override suspend fun refreshToken() {

    }

    override suspend fun rememberUser(isLogged: Boolean, email: String) {
        storage.rememberUser(isLogged, email)
    }

    override suspend fun register(authModel: AuthModel): Boolean {
        return true
    }

    override fun isLogged(): Flow<Boolean> {
        return storage.isUserLogged()
    }

}