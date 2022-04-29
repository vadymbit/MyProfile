package com.vadym.myprofile.presentation.ui.profile.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
        emitSource(source = onFlowResult(result = getUserProfileUseCase()))
    }.map { ProfileMapper.toPresentation(it) }

    private val _profilePhoto = MutableLiveData<String>()
    val profilePhoto: LiveData<String>
        get() = _profilePhoto

    fun setProfilePhoto(photoPath: String) {
        _profilePhoto.value = photoPath
    }

    fun isInputValid(
        userNameErrorEnabled: Boolean,
        phoneErrorEnabled: Boolean
    ): Boolean {
        return !userNameErrorEnabled && !phoneErrorEnabled
    }

    fun saveProfile(
        username: String,
        career: String?,
        phone: String,
        address: String?,
        birthDate: String?,
        facebook: String?,
        instagram: String?,
        twitter: String?,
        linkedin: String?
    ) {
        launch {
            editProfileUseCase.invoke(
                ProfileMapper.toDomain(
                    ProfileModel(
                        name = username,
                        phoneNumber = phone,
                        career = career.orEmpty(),
                        address = address.orEmpty(),
                        birthDate = birthDate.orEmpty(),
                        urlPhoto = profilePhoto.value.orEmpty(),
                        facebook = facebook.orEmpty(),
                        instagram = instagram.orEmpty(),
                        twitter = twitter.orEmpty(),
                        linkedin = linkedin.orEmpty()
                    )
                )
            )
        }
    }
}