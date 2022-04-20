package com.vadym.myprofile.presentation.ui.main

import androidx.lifecycle.asLiveData
import com.vadym.myprofile.app.base.BaseViewModel
import com.vadym.myprofile.domain.useCase.auth.IsLoggedUserUseCase
import com.vadym.myprofile.domain.useCase.auth.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    isLoggedUserUseCase: IsLoggedUserUseCase,
) : BaseViewModel() {
    val isLogged = isLoggedUserUseCase().asLiveData()

    fun logout() {
        launch {
            logoutUseCase()
        }
    }
}