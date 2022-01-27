package com.vadym.myprofile.app.di

import android.content.Context
import com.vadym.myprofile.data.repository.AuthRepositoryImpl
import com.vadym.myprofile.data.repository.UserRepositoryImpl
import com.vadym.myprofile.data.storage.LocalPrefsStore
import com.vadym.myprofile.domain.repository.AuthRepository
import com.vadym.myprofile.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideAuthRepository(storage: LocalPrefsStore): AuthRepository {
        return AuthRepositoryImpl(storage = storage)
    }

    @Provides
    @Singleton
    fun provideUserRepository(storage: LocalPrefsStore): UserRepository {
        return UserRepositoryImpl(storage = storage)
    }

    @Provides
    @Singleton
    fun provideLocalStorage(@ApplicationContext context: Context): LocalPrefsStore {
        return LocalPrefsStore(context)
    }
}