package com.vadym.myprofile.data.repository

import com.vadym.myprofile.data.model.ApiResult
import com.vadym.myprofile.data.model.mapper.AuthMapper
import com.vadym.myprofile.data.source.local.AuthLocalStorage
import com.vadym.myprofile.data.source.local.ContactLocalStorage
import com.vadym.myprofile.data.source.remote.ApiService
import com.vadym.myprofile.domain.model.AuthModel
import com.vadym.myprofile.domain.model.Result
import com.vadym.myprofile.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authLocalSource: AuthLocalStorage,
    private val usersLocalStorage: ContactLocalStorage,
    private val apiService: ApiService
) : AuthRepository, BaseRepository() {

    override suspend fun register(authModel: AuthModel): Result<Boolean, Throwable> {
        val apiResult = getRequestResult { apiService.register(AuthMapper.toAuthRequest(authModel)) }
        return getResult(apiResult) {
            authLocalSource.saveProfileData(
                userAccessToken = (apiResult as ApiResult.Success).data.accessToken,
                userRefreshToken = apiResult.data.refreshToken,
                id = apiResult.data.user.id
            )
        }
    }

    override suspend fun login(authModel: AuthModel): Result<Boolean, Throwable> {
        val apiResult = getRequestResult { apiService.login(AuthMapper.toAuthRequest(authModel)) }
        return getResult(apiResult) {
            authLocalSource.saveProfileData(
                userAccessToken = (apiResult as ApiResult.Success).data.accessToken,
                userRefreshToken = apiResult.data.refreshToken,
                id = apiResult.data.user.id
            )
        }
    }

    override suspend fun logout() {
        withContext(Dispatchers.IO) {
            usersLocalStorage.removeAll()
            authLocalSource.clearProfileData()
        }
    }

    override suspend fun rememberUser() {
        authLocalSource.rememberUser()
    }

    override fun isLogged(): Flow<Boolean> {
        return authLocalSource.isUserLogged()
    }
}