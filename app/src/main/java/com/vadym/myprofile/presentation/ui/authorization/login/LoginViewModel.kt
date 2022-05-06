package com.vadym.myprofile.presentation.ui.authorization.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.vadym.myprofile.app.base.BaseViewModel
import com.vadym.myprofile.domain.useCase.auth.IsLoggedUserUseCase
import com.vadym.myprofile.domain.useCase.auth.LoginUseCase
import com.vadym.myprofile.domain.useCase.auth.RememberUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel  @Inject constructor(
    private val loginUseCase: LoginUseCase,
    isLoggedUserUseCase: IsLoggedUserUseCase,
    private val rememberUserUseCase: RememberUserUseCase
) : BaseViewModel() {

    private val isRemembered: LiveData<Boolean> = isLoggedUserUseCase().asLiveData()
    private val isLogged: MutableLiveData<Boolean> = MutableLiveData()
    private val _navigateToProfile = MediatorLiveData<Boolean>()
    val navigateToProfile: LiveData<Boolean>
        get() = _navigateToProfile

    init {
        _navigateToProfile.value = false
        _navigateToProfile.addSource(isRemembered) { value ->
            if (navigateToProfile.value == false && value) {
                _navigateToProfile.value = value
            }
        }
        _navigateToProfile.addSource(isLogged) { value ->
            if (navigateToProfile.value == false && value) {
                _navigateToProfile.value = value
            }
        }
    }

    fun isInputValid(emailErrorEnabled: Boolean, passErrorEnabled: Boolean): Boolean {
        return !emailErrorEnabled && !passErrorEnabled
    }

    fun login(email: String, password: String) {
        launch {
            isLoading.value = true
            val result = loginUseCase.invoke(email, password)
            onResult(result, isLogged)
            isLoading.value = false
        }
    }

    fun isRememberUser(isRemember: Boolean) {
        if (isRemember) {
            launch {
                rememberUserUseCase()
            }
        }
    }
}