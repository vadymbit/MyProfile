package com.example.myprofile.ui.authorization

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.commit
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.myprofile.R
import com.example.myprofile.base.BaseFragment
import com.example.myprofile.databinding.FragmentAuthBinding
import com.example.myprofile.ui.ProfileFragment
import com.example.myprofile.ui.ProfileFragmentDirections
import com.example.myprofile.utils.EMAIL
import com.example.myprofile.utils.MAX_PASS_LENGTH
import com.example.myprofile.utils.MIN_PASS_LENGTH
import com.example.myprofile.utils.featureNavigationEnabled

class AuthFragment : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {

    override fun initialize() {
        autoLogin()
    }

    override fun setUpListeners() {
        binding.apply {
            etEmail.addTextChangedListener { validateEmail() }
            etPass.addTextChangedListener { isPasswordValid() }
            btnRegister.setOnClickListener { goToMyProfile() }
            tvSignIn.setOnClickListener { goToLoginFragment() }
            btnLoginViaSocial.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "Login via Google",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun goToMyProfile() {
        if (isInputValidate()) {
            if (binding.cbRememberMe.isChecked) {
                rememberUser()
            }
            if (featureNavigationEnabled) {
                val action =
                    AuthFragmentDirections.actionAuthFragmentToProfileFragment(binding.etEmail.text.toString())
                findNavController().navigate(action)
            } else {
                val bundle = Bundle()
                bundle.putString(EMAIL, binding.etEmail.text.toString())
                parentFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace(R.id.fragmentContainerView, ProfileFragment::class.java, bundle)
                }
            }
        }
    }

    /**
     * Check if input password and email is valid
     *
     * @return True if they valid
     */
    private fun isInputValidate(): Boolean {
        return validateEmail() && isPasswordValid()
    }

    /**
     * Validate input email address
     *
     * @return True if email correct or is not empty
     */
    private fun validateEmail(): Boolean {
        binding.apply {
            if (etEmail.text.isNullOrBlank()) {
                tiEmail.error = getString(R.string.auth_error_required)
                etEmail.requestFocus()
                return false
            } else if (!isEmailValid(etEmail.text.toString())) {
                tiEmail.error = getString(R.string.auth_error_incorrect_email)
                etEmail.requestFocus()
                return false
            } else {
                tiEmail.isErrorEnabled = false
            }
        }
        return true
    }

    /**
     * Check if the email matches the pattern
     *
     * @return True if the email matches the pattern
     */
    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Validate input password
     *
     * @return True if length of password more than [MIN_PASS_LENGTH] and less than [MAX_PASS_LENGTH]
     */
    private fun isPasswordValid(): Boolean {
        binding.apply {
            when {
                etPass.text.isNullOrBlank() -> {
                    tiPass.error = getString(R.string.auth_error_required)
                    etPass.requestFocus()
                    return false
                }
                etPass.text.toString().length < MIN_PASS_LENGTH -> {
                    tiPass.error =
                        getString(R.string.auth_error_short_password, MIN_PASS_LENGTH)
                    etPass.requestFocus()
                    return false
                }
                etPass.text.toString().length > MAX_PASS_LENGTH -> {
                    tiPass.error =
                        getString(R.string.auth_error_big_password, MAX_PASS_LENGTH)
                    etPass.requestFocus()
                    return false
                }
                else -> {
                    tiPass.isErrorEnabled = false
                }
            }
        }
        return true
    }

    /**
     * Go to login activity
     */
    private fun goToLoginFragment() {
        //todo create login fragment
    }

    /**
     * Save user email and password to shared preferences
     */
    private fun rememberUser() {
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        sharedPref.edit().apply {
            putString(getString(R.string.user_email), binding.etEmail.text.toString())
            putString(getString(R.string.user_pass), binding.etPass.text.toString())
            apply()
        }
    }

    /**
     * Go to profile activity if user saved
     */
    private fun autoLogin() {
        val pass = getString(R.string.user_pass)
        val email = getString(R.string.user_email)
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        if (!sharedPref.contains(pass) || !sharedPref.contains(email)) {
            return
        }
        binding.apply {
            etEmail.setText(sharedPref.getString(email, ""))
            etPass.setText(sharedPref.getString(pass, ""))
            btnRegister.performClick()
        }
    }
}