package com.vadym.myprofile.data.source.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.vadym.myprofile.data.Const.ACCESS_TOKEN
import com.vadym.myprofile.data.Const.IS_LOGGED
import com.vadym.myprofile.data.Const.PROFILE_ID
import com.vadym.myprofile.data.Const.REFRESH_TOKEN
import com.vadym.myprofile.data.Const.STORE_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class AuthLocalStorage(private val context: Context) {
    private val loginStatus = booleanPreferencesKey(IS_LOGGED)
    private val profileID = intPreferencesKey(PROFILE_ID)
    private val accessToken = stringPreferencesKey(ACCESS_TOKEN)
    private val refreshToken = stringPreferencesKey(REFRESH_TOKEN)
    private val Context.dataStore by preferencesDataStore(
        name = STORE_NAME
    )

    suspend fun saveProfileData(userAccessToken: String, userRefreshToken: String, id: Int? = null): Boolean {
        context.dataStore.edit { preferences ->
            preferences[accessToken] = userAccessToken
            preferences[refreshToken] = userRefreshToken
            id?.let { preferences[profileID] = it }
        }
        return true
    }

    fun getAccessToken(): String? {
        return runBlocking {
            context.dataStore.data.map { preferences ->
                preferences[accessToken]
            }.firstOrNull()
        }
    }

    fun getRefreshToken(): String? {
        return runBlocking {
            context.dataStore.data.map { preferences ->
                preferences[refreshToken]
            }.firstOrNull()
        }
    }

    fun getProfileId(): Int {
        return runBlocking {
            context.dataStore.data.map { preferences ->
                preferences[profileID] ?: -1
            }.first()
        }
    }

    suspend fun rememberUser() {
        context.dataStore.edit { preferences ->
            preferences[loginStatus] = true
        }
    }

    suspend fun clearProfileData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    fun isUserLogged(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[loginStatus] ?: false
        }
    }
}