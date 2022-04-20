package com.vadym.myprofile.presentation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.vadym.myprofile.app.base.BaseViewModel
import com.vadym.myprofile.domain.useCase.profile.GetUserProfileUseCase
import com.vadym.myprofile.presentation.model.ProfileModel
import com.vadym.myprofile.presentation.model.mapper.ProfileMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase
) : BaseViewModel() {

    val profile: LiveData<ProfileModel> = liveData {
        emitSource(onFlowResult(getUserProfileUseCase()))
    }.map { ProfileMapper.toPresentation(it) }
}