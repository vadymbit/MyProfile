package com.vadym.myprofile.presentation.ui.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.vadym.myprofile.app.base.BaseViewModel
import com.vadym.myprofile.domain.useCase.auth.IsLoggedUserUseCase
import com.vadym.myprofile.domain.useCase.auth.LoginUseCase
import com.vadym.myprofile.domain.useCase.auth.RegisterUseCase
import com.vadym.myprofile.domain.useCase.auth.RememberUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthSharedViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase,
    isLoggedUserUseCase: IsLoggedUserUseCase,
    private val rememberUserUseCase: RememberUserUseCase
) : BaseViewModel() {

    private val isRemembered: LiveData<Boolean> = isLoggedUserUseCase().asLiveData()
    private val isRegistered: MutableLiveData<Boolean> = MutableLiveData()
    private val isLogged: MutableLiveData<Boolean> = MutableLiveData()
    val navigateToProfile = MediatorLiveData<Boolean>()

    init {
        navigateToProfile.value = false
        navigateToProfile.addSource(isRemembered) { value ->
            if (navigateToProfile.value == false && value) {
                navigateToProfile.value = value
            }
        }
        navigateToProfile.addSource(isRegistered) { value ->
            if (navigateToProfile.value == false && value) {
                navigateToProfile.value = value
            }
        }
        navigateToProfile.addSource(isLogged) { value ->
            if (navigateToProfile.value == false && value) {
                navigateToProfile.value = value
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