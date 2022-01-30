package com.vadym.myprofile.presentation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.vadym.myprofile.app.base.BaseViewModel
import com.vadym.myprofile.domain.useCase.GetUserEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    getUserEmailUseCase: GetUserEmailUseCase
) : BaseViewModel() {

    val email: LiveData<String> = getUserEmailUseCase().asLiveData()

}