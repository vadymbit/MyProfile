package com.vadym.myprofile.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vadym.myprofile.app.base.BaseViewModel
import com.vadym.myprofile.domain.useCase.auth.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
) : BaseViewModel() {
    private val _isLogout = MutableLiveData(false)
    val isLogout: LiveData<Boolean>
        get() = _isLogout

    fun logout() {
        launch {
            onResult(logoutUseCase(), _isLogout)
        }
    }
}