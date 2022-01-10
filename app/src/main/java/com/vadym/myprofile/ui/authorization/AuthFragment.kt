package com.vadym.myprofile.ui.authorization

import android.content.Context
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.vadym.myprofile.R
import com.vadym.myprofile.base.BaseFragment
import com.vadym.myprofile.databinding.FragmentAuthBinding
import com.vadym.myprofile.utils.ext.safeNavigation
import com.vadym.myprofile.utils.ext.addValidateEmailListener
import com.vadym.myprofile.utils.ext.addValidatePasswordListener

class AuthFragment : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {

    override fun initialize() {
        autoLogin()
    }

    override fun setUpListeners() {
        binding.apply {
            tiPass.addValidatePasswordListener()
            tiEmail.addValidateEmailListener()
            btnRegister.setOnClickListener {
                if (isInputValidate()) {
                    if (cbRememberMe.isChecked) {
                        rememberUser()
                    }
                    goToMyProfile(binding.etEmail.text.toString())
                }
            }
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

    private fun goToMyProfile(email: String) {
        val action = AuthFragmentDirections.actionAuthFragmentToMainFragment(email)
        findNavController().safeNavigation(action)
    }

    /**
     * Check if input password and email is valid
     *
     * @return True if they valid
     */
    private fun isInputValidate(): Boolean {
        return !binding.tiEmail.isErrorEnabled && !binding.tiPass.isErrorEnabled
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
        if (sharedPref.contains(pass) && sharedPref.contains(email)) {
            goToMyProfile(sharedPref.getString(email, "")!!)
        }
    }
}