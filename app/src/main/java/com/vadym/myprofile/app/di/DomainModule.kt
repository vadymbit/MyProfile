package com.vadym.myprofile.app.di

import com.vadym.myprofile.domain.repository.AuthRepository
import com.vadym.myprofile.domain.repository.ContactRepository
import com.vadym.myprofile.domain.repository.ProfileRepository
import com.vadym.myprofile.domain.useCase.auth.*
import com.vadym.myprofile.domain.useCase.contact.*
import com.vadym.myprofile.domain.useCase.profile.EditProfileUseCase
import com.vadym.myprofile.domain.useCase.profile.GetProfileIdUseCase
import com.vadym.myprofile.domain.useCase.profile.GetUserProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideRegisterUseCase(authRepository: AuthRepository): RegisterUseCase {
        return RegisterUseCase(authRepository)
    }

    @Provides
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Provides
    fun provideLogoutUseCase(authRepository: AuthRepository): LogoutUseCase {
        return LogoutUseCase(authRepository)
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
    fun provideAddContactUseCase(contactRepository: ContactRepository): AddContactUseCase {
        return AddContactUseCase(contactRepository)
    }

    @Provides
    fun provideGetUserContactUseCase(contactRepository: ContactRepository): GetUserContactUseCase {
        return GetUserContactUseCase(contactRepository)
    }

    @Provides
    fun provideSearchByNameUseCase(contactRepository: ContactRepository): SearchByNameUseCase {
        return SearchByNameUseCase(contactRepository)
    }

    @Provides
    fun provideRemoveContactUseCase(contactRepository: ContactRepository): RemoveContactUseCase {
        return RemoveContactUseCase(contactRepository)
    }

    @Provides
    fun provideGetAllUsersUseCase(contactRepository: ContactRepository): GetAllUsersUseCase {
        return GetAllUsersUseCase(contactRepository)
    }

    @Provides
    fun provideEditProfileUseCase(userRepository: ProfileRepository): EditProfileUseCase {
        return EditProfileUseCase(userRepository)
    }

    @Provides
    fun provideGetUserProfileUseCase(userRepository: ProfileRepository): GetUserProfileUseCase {
        return GetUserProfileUseCase(userRepository)
    }

    @Provides
    fun provideProfileIdUseCase(userRepository: ProfileRepository): GetProfileIdUseCase {
        return GetProfileIdUseCase(userRepository)
    }
}