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
class AuthViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    isLoggedUserUseCase: IsLoggedUserUseCase,
    private val rememberUserUseCase: RememberUserUseCase
) : BaseViewModel() {

    private val isRemembered: LiveData<Boolean> = isLoggedUserUseCase().asLiveData()
    private val isRegistered = MutableLiveData<Boolean>()
    val isLogged = MediatorLiveData<Boolean>()

    init {
        isLogged.value = false
        isLogged.addSource(isRemembered) { value ->
            if (isLogged.value == false && value) {
                isLogged.value = value
            }
        }
        isLogged.addSource(isRegistered) { value ->
            if (isLogged.value == false && value) {
                isLogged.value = value
            }
        }
    }

    fun isInputValid(emailErrorEnabled: Boolean, passErrorEnabled: Boolean): Boolean {
        return !emailErrorEnabled && !passErrorEnabled
    }

    fun register(email: String, password: String) {
        launch {
            isLoading.value = true
            isRegistered.value = registerUseCase.invoke(email, password)
            isLoading.value = false
        }
    }

    fun rememberUser(isLogged: Boolean, email: String) {
        launch {
            rememberUserUseCase(isLogged, email)
        }
    }
}