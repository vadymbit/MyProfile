package com.vadym.myprofile.presentation.ui.authorization.login

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.vadym.myprofile.app.base.BaseFragment
import com.vadym.myprofile.app.utils.ext.*
import com.vadym.myprofile.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    override fun setObservers() {
        viewModel.apply {
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
                    requireContext().showToast(it)
                }
            }
            isLoading.observe(viewLifecycleOwner) {
                binding.btnLogin.isEnabled = !it
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            tiPass.removeErrorIfNotFocused()
            tiEmail.removeErrorIfNotFocused()
            etPass.setOnEditorActionListener { textView, actionId, _ ->
                return@setOnEditorActionListener when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        textView.clearFocus()
                        requireActivity().hideKeyboard()
                        true
                    }
                    else -> false
                }
            }
            btnLogin.setOnClickListener {
                tiPass.validatePassword()
                tiEmail.validateEmail()
                if (isInputValid()) {
                    viewModel.login(etEmail.text.toString(), etPass.text.toString())
                    viewModel.isRememberUser(cbRememberMe.isChecked)
                }
            }
            tvRegister.setOnClickListener { goToRegister() }
        }
    }

    private fun isInputValid(): Boolean {
        return viewModel.isInputValid(
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