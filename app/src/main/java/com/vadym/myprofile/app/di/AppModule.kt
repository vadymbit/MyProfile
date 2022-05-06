package com.vadym.myprofile.app.di

import android.content.Context
import com.vadym.myprofile.app.utils.NetworkErrorParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideNetworkErrorParser(@ApplicationContext context: Context): NetworkErrorParser {
        return NetworkErrorParser(context = context)
    }
}