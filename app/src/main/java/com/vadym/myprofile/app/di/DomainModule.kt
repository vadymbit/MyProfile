package com.vadym.myprofile.app.di

import com.vadym.myprofile.domain.repository.AuthRepository
import com.vadym.myprofile.domain.repository.ContactRepository
import com.vadym.myprofile.domain.repository.UserRepository
import com.vadym.myprofile.domain.useCase.GetUserEmailUseCase
import com.vadym.myprofile.domain.useCase.auth.IsLoggedUserUseCase
import com.vadym.myprofile.domain.useCase.auth.RegisterUseCase
import com.vadym.myprofile.domain.useCase.auth.RememberUserUseCase
import com.vadym.myprofile.domain.useCase.contact.AddContactUseCase
import com.vadym.myprofile.domain.useCase.contact.GetUserContactUseCase
import com.vadym.myprofile.domain.useCase.contact.RemoveContactUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun provideRegisterUseCase(authRepository: AuthRepository): RegisterUseCase {
        return RegisterUseCase(authRepository)
    }

    @Provides
    fun provideRememberUserUseCase(authRepository: AuthRepository): RememberUserUseCase {
        return RememberUserUseCase(authRepository)
    }

    @Provides
    fun provideIsLoggedUserUseCase(authRepository: AuthRepository): IsLoggedUserUseCase {
        return IsLoggedUserUseCase(authRepository)
    }

    @Provides
    fun provideGetUserEmailUseCase(userRepository: UserRepository): GetUserEmailUseCase {
        return GetUserEmailUseCase(userRepository)
    }

    @Provides
    fun provideGetUserContactUseCase(contactRepository: ContactRepository): GetUserContactUseCase {
        return GetUserContactUseCase(contactRepository)
    }

    @Provides
    fun provideRemoveContactUseCase(contactRepository: ContactRepository): RemoveContactUseCase {
        return RemoveContactUseCase(contactRepository)
    }

    @Provides
    fun provideAddContactUseCase(contactRepository: ContactRepository): AddContactUseCase {
        return AddContactUseCase(contactRepository)
    }
}