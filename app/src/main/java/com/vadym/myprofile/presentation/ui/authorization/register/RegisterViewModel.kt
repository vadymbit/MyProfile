package com.vadym.myprofile.presentation.ui.authorization.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.vadym.myprofile.app.base.BaseViewModel
import com.vadym.myprofile.domain.useCase.auth.IsLoggedUserUseCase
import com.vadym.myprofile.domain.useCase.auth.RegisterUseCase
import com.vadym.myprofile.domain.useCase.auth.RememberUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel  @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    isLoggedUserUseCase: IsLoggedUserUseCase,
    private val rememberUserUseCase: RememberUserUseCase
) : BaseViewModel() {

    private val isRemembered: LiveData<Boolean> = isLoggedUserUseCase().asLiveData()
    private val isRegistered: MutableLiveData<Boolean> = MutableLiveData()
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
        _navigateToProfile.addSource(isRegistered) { value ->
            if (navigateToProfile.value == false && value) {
                _navigateToProfile.value = value
            }
        }
    }

    fun isInputValid(emailErrorEnabled: Boolean, passErrorEnabled: Boolean): Boolean {
        return !emailErrorEnabled && !passErrorEnabled
    }

    fun register(email: String, password: String) {
        launch {
            isLoading.value = true
            val result = registerUseCase.invoke(email, password)
            onResult(result, isRegistered)
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