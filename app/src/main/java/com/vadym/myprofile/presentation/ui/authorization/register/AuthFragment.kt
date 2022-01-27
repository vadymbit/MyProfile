package com.vadym.myprofile.presentation.ui.authorization.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vadym.myprofile.app.base.BaseFragment
import com.vadym.myprofile.databinding.FragmentAuthBinding
import com.vadym.myprofile.app.utils.ext.safeNavigation
import com.vadym.myprofile.app.utils.ext.addValidateEmailListener
import com.vadym.myprofile.app.utils.ext.addValidatePasswordListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {

    private val viewModel by viewModels<AuthViewModel>()

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
                    viewModel.rememberUser(
                        cbRememberMe.isChecked,
                        etEmail.text.toString()
                    )
                    viewModel.register(etEmail.text.toString(), etPass.text.toString())
                }
            }
            btnLoginViaSocial.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "Login via Google",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun setObservers() {
        viewModel.isLogged.observe(this) {
            if (it) {
                goToMyProfile()
            }
        }
    }

    private fun goToMyProfile() {
        val action = AuthFragmentDirections.actionAuthFragmentToMainActivity()
        findNavController().safeNavigation(action)
        activity?.finish()
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