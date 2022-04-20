package com.vadym.myprofile.data.source.remote

import android.util.Log
import com.vadym.myprofile.data.source.local.AuthLocalStorage
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val serviceHolder: ApiServiceHolder,
    private val storage: AuthLocalStorage
) :
    Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            val tokenResponse = refreshToken()
            when {
                 !tokenResponse.isNullOrEmpty() -> {
                     Log.d("Debuging", "Refresh token $tokenResponse")
                     response.request().newBuilder()
                         .header("Authorization", tokenResponse)
                         .build()
                 } else -> null
            }
        }
    }

    private suspend fun refreshToken(): String? {
        val refreshToken = storage.getRefreshToken()
        val response = refreshToken?.let { serviceHolder.tokenService?.refreshToken(it) }
        if (response != null && response.isSuccessful) {
            if (response.body()?.code == 200) {
                val data = response.body()?.data
                if (data != null) {
                    storage.saveProfileData(data.accessToken, data.refreshToken)
                    return data.refreshToken
                }
            } else {
                //storage.logOut()
            }
        }
        return null
    }
}