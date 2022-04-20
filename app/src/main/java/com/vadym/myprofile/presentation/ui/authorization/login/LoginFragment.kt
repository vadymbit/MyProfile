package com.vadym.myprofile.presentation.ui.authorization.login

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
import com.vadym.myprofile.databinding.FragmentLoginBinding
import com.vadym.myprofile.presentation.ui.authorization.AuthSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val authSharedViewModel: AuthSharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    override fun setObservers() {
        authSharedViewModel.apply {
            navigateToProfile.observe(viewLifecycleOwner) { isLogged ->
                if (isLogged) {
                    goToMyProfile()
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
                binding.btnLogin.isEnabled = !it
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            tiEmail.addValidateEmailListener()
            tiPass.addValidatePasswordListener()
            btnLogin.setOnClickListener {
                if (isInputValid()) {
                    authSharedViewModel.login(etEmail.text.toString(), etPass.text.toString())
                    authSharedViewModel.isRememberUser(cbRememberMe.isChecked)
                }
            }
            tvRegister.setOnClickListener { goToRegister() }
        }
    }

    private fun isInputValid(): Boolean {
        return authSharedViewModel.isInputValid(
            binding.tiEmail.isErrorEnabled,
            binding.tiPass.isErrorEnabled
        )
    }

    private fun goToMyProfile() {
        val action = LoginFragmentDirections.actionLoginFragmentToMainActivity()
        findNavController().safeNavigation(action)
        activity?.finish()
    }

    private fun goToRegister() {
        findNavController().safeNavigation(LoginFragmentDirections.actionLoginFragmentToAuthFragment())
    }
}