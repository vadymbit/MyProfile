package com.vadym.myprofile.presentation.ui.profile.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.vadym.myprofile.app.base.BaseViewModel
import com.vadym.myprofile.domain.useCase.profile.EditProfileUseCase
import com.vadym.myprofile.domain.useCase.profile.GetUserProfileUseCase
import com.vadym.myprofile.presentation.model.ProfileModel
import com.vadym.myprofile.presentation.model.mapper.ProfileMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val editProfileUseCase: EditProfileUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase
) : BaseViewModel() {

    val profile: LiveData<ProfileModel> = liveData {
        emitSource(onFlowResult(getUserProfileUseCase()))
    }.map { ProfileMapper.toPresentation(it) }

    fun isInputValid(
        addressErrorEnabled: Boolean,
        userNameErrorEnabled: Boolean,
        phoneErrorEnabled: Boolean
    ): Boolean {
        return !addressErrorEnabled && !userNameErrorEnabled && !phoneErrorEnabled
    }

    fun saveProfile(
        username: String,
        career: String?,
        phone: String,
        address: String?,
        birthDate: String?,
        profilePhoto: String?
    ) {
        launch {
            editProfileUseCase.invoke(
                ProfileMapper.toDomain(
                    ProfileModel(
                        username,
                        phone,
                        career.orEmpty(),
                        address.orEmpty(),
                        birthDate.orEmpty(),
                        profilePhoto.orEmpty()
                    )
                )
            )
        }
    }
}