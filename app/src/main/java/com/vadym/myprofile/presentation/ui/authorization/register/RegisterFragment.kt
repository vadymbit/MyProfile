package com.vadym.myprofile.presentation.ui.authorization.register

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
import com.vadym.myprofile.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setListeners()
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
            btnRegister.setOnClickListener {
                tiPass.validatePassword()
                tiEmail.validateEmail()
                if (isInputValid()) {
                    viewModel.register(etEmail.text.toString(), etPass.text.toString())
                    viewModel.isRememberUser(cbRememberMe.isChecked)
                }
            }
            tvSignIn.setOnClickListener { goToLogin() }
        }
    }

    override fun setObservers() {
        viewModel.apply {
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
                    requireContext().showToast(it)
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
            safeNavigation(RegisterFragmentDirections.actionAuthFragmentToLoginFragment())
        }
    }

    /**
     * Check if input password and email is valid
     *
     * @return True if they valid
     */
    private fun isInputValid(): Boolean {
        return viewModel.isInputValid(
            binding.tiEmail.isErrorEnabled,
            binding.tiPass.isErrorEnabled
        )
    }
}