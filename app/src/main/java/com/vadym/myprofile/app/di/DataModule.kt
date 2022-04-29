package com.vadym.myprofile.app.di

import android.content.Context
import androidx.room.Room
import com.vadym.myprofile.app.utils.Constants.BASE_URL
import com.vadym.myprofile.app.utils.Constants.DB_NAME
import com.vadym.myprofile.data.repository.AuthRepositoryImpl
import com.vadym.myprofile.data.repository.ContactRepositoryImpl
import com.vadym.myprofile.data.repository.ProfileRepositoryImpl
import com.vadym.myprofile.data.source.local.*
import com.vadym.myprofile.data.source.local.db.AppDatabase
import com.vadym.myprofile.data.source.local.db.UsersDao
import com.vadym.myprofile.data.source.remote.ApiService
import com.vadym.myprofile.data.source.remote.ApiServiceHolder
import com.vadym.myprofile.data.source.remote.TokenAuthenticator
import com.vadym.myprofile.data.source.remote.TokenInterceptor
import com.vadym.myprofile.domain.repository.AuthRepository
import com.vadym.myprofile.domain.repository.ContactRepository
import com.vadym.myprofile.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    fun provideDatabaseDao(db: AppDatabase): UsersDao {
        return db.usersDao()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        storage: AuthLocalStorage,
        userStorage: ContactLocalStorage,
        apiService: ApiService
    ): AuthRepository {
        return AuthRepositoryImpl(storage, userStorage, apiService)
    }

    @Provides
    @Singleton
    fun provideContactRepository(
        storage: ContactLocalStorage,
        apiService: ApiService,
        authStorage: AuthLocalStorage
    ): ContactRepository {
        return ContactRepositoryImpl(storage = storage, apiService = apiService, authStorage)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        storage: ProfileLocalStorage,
        authStorage: AuthLocalStorage,
        apiService: ApiService
    ): ProfileRepository {
        return ProfileRepositoryImpl(storage, authStorage, apiService)
    }

    @Provides
    @Singleton
    fun provideUserLocalStorage(usersDao: UsersDao): ContactLocalStorage {
        return ContactLocalStorageImpl(usersDao)
    }

    @Provides
    @Singleton
    fun provideAuthLocalStorage(@ApplicationContext context: Context): AuthLocalStorage {
        return AuthLocalStorageImpl(context)
    }

    @Provides
    @Singleton
    fun provideProfileLocalStorage(usersDao: UsersDao): ProfileLocalStorage {
        return ProfileLocalStorageImpl(usersDao)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        val service = retrofit.create(ApiService::class.java)
        ApiServiceHolder.tokenService = service
        return service
    }

    @Provides
    @Singleton
    fun provideOkhttp(storage: AuthLocalStorage): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(TokenInterceptor(storage))
            .authenticator(TokenAuthenticator(ApiServiceHolder, storage))
            .build()
    }
}