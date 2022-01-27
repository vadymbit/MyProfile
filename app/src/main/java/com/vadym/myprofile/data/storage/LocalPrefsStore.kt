package com.vadym.myprofile.data.storage

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.vadym.myprofile.app.utils.Constants.LOCAL_PREFS
import com.vadym.myprofile.app.utils.Constants.IS_LOGGED
import com.vadym.myprofile.app.utils.Constants.USER_EMAIL
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalPrefsStore(val context: Context) {
    private val loginStatus = booleanPreferencesKey(IS_LOGGED)
    private val userEmail = stringPreferencesKey(USER_EMAIL)
    private val Context.dataStore by preferencesDataStore(
        name = LOCAL_PREFS
    )

    suspend fun rememberUser(isLogged: Boolean, email: String) {
        context.dataStore.edit { preferences ->
            preferences[loginStatus] = isLogged
            preferences[userEmail] = email
        }
    }

    fun isUserLogged(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[loginStatus] ?: false
        }
    }

    fun getUserEmail(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[userEmail] ?: ""
        }
    }
}