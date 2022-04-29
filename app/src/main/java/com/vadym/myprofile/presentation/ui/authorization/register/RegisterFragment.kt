package com.vadym.myprofile.presentation.ui.authorization.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.vadym.myprofile.app.base.BaseFragment
import com.vadym.myprofile.app.utils.ext.addValidateEmailListener
import com.vadym.myprofile.app.utils.ext.addValidatePasswordListener
import com.vadym.myprofile.app.utils.ext.safeNavigation
import com.vadym.myprofile.databinding.FragmentRegisterBinding
import com.vadym.myprofile.presentation.ui.authorization.AuthSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val authSharedViewModel by activityViewModels<AuthSharedViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        binding.apply {
            tiPass.addValidatePasswordListener()
            tiEmail.addValidateEmailListener()
            btnRegister.setOnClickListener {
                if (isInputValid()) {
                    authSharedViewModel.register(etEmail.text.toString(), etPass.text.toString())
                    authSharedViewModel.isRememberUser(cbRememberMe.isChecked)
                }
            }
            btnLoginViaSocial.setOnClickListener {
                showToast("Login via Google")
            }
            tvSignIn.setOnClickListener { goToLogin() }
        }
    }

    override fun setObservers() {
        authSharedViewModel.apply {
            navigateToProfile.observe(viewLifecycleOwner) { isLogged ->
                if (isLogged) {
                    goToEditProfile()
                }
            }
            lifecycleScope.launch {
                eventsFlow.flowWithLifecycle(
                    viewLifecycleOwner.lifecycle,
                    Lifecycle.State.RESUMED
                ).collect {
                    showToast(it)
                }
            }
            isLoading.observe(viewLifecycleOwner) {
                binding.btnRegister.isEnabled = !it
            }
        }
    }

    private fun goToEditProfile() {
        findNavController().safeNavigation(RegisterFragmentDirections.actionAuthFragmentToEditFragment())
    }

    private fun goToLogin() {
        findNavController().apply {
            if (previousBackStackEntry != null) {
                navigateUp()
            } else {
                safeNavigation(RegisterFragmentDirections.actionAuthFragmentToLoginFragment())
            }
        }
    }

    /**
     * Check if input password and email is valid
     *
     * @return True if they valid
     */
    private fun isInputValid(): Boolean {
        return authSharedViewModel.isInputValid(
            binding.tiEmail.isErrorEnabled,
            binding.tiPass.isErrorEnabled
        )
    }
}