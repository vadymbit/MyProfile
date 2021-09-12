package com.example.myprofile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import com.example.myprofile.databinding.ActivityAuthBinding

/**
 * The name of the extra data, with package prefix for intent
 */
const val EMAIL = "com.example.myprofile.EMAIL"

/**
 * Min length of user password
 */
const val MIN_PASS_LENGTH = 8

/**
 * Max length of user password
 */
const val MAX_PASS_LENGTH = 16

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        autoLogin()
        setupListeners()
    }

    /**
     * Go to profile activity after validating password and email
     */
    private fun goToMyProfile() {
        if (isInputValidate()) {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra(EMAIL, binding.editTextEmail.text.toString())
            }
            if (binding.checkBoxRememberMe.isChecked) {
                rememberUser()
            }
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
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
            if (editTextEmail.text.toString().trim().isEmpty()) {
                textInputEmail.error = getString(R.string.auth_error_required)
                editTextEmail.requestFocus()
                return false
            } else if (!isEmailValid(editTextEmail.text.toString())) {
                textInputEmail.error = getString(R.string.auth_error_incorrect_email)
                editTextEmail.requestFocus()
                return false
            } else {
                textInputEmail.isErrorEnabled = false
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
                editTextPass.text.toString().trim().isEmpty() -> {
                    textInputPass.error = getString(R.string.auth_error_required)
                    editTextPass.requestFocus()
                    return false
                }
                editTextPass.text.toString().length < MIN_PASS_LENGTH -> {
                    textInputPass.error =
                        getString(R.string.auth_error_short_password, MIN_PASS_LENGTH)
                    editTextPass.requestFocus()
                    return false
                }
                editTextPass.text.toString().length > MAX_PASS_LENGTH -> {
                    textInputPass.error =
                        getString(R.string.auth_error_big_password, MAX_PASS_LENGTH)
                    editTextPass.requestFocus()
                    return false
                }
                else -> {
                    textInputPass.isErrorEnabled = false
                }
            }
        }
        return true
    }

    /**
     * Setup listeners for input fields email and password
     */
    private fun setupListeners() {
        binding.apply {
            editTextEmail.addTextChangedListener(TextFieldValidation(editTextEmail))
            editTextPass.addTextChangedListener(TextFieldValidation(editTextPass))
            buttonRegister.setOnClickListener { goToMyProfile() }
            textViewSignIn.setOnClickListener { goToLoginActivity() }
        }
    }

    /**
     * Go to login activity
     */
    private fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Save user email and password to shared preferences
     */
    private fun rememberUser() {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        sharedPref.edit().apply {
            putString(getString(R.string.user_email), binding.editTextEmail.text.toString())
            putString(getString(R.string.user_pass), binding.editTextPass.text.toString())
            apply()
        }
    }

    /**
     * Go to profile activity is user saved
     */
    private fun autoLogin() {
        val pass = getString(R.string.user_pass)
        val email = getString(R.string.user_email)
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        if (!sharedPref.contains(pass) || !sharedPref.contains(email)) {
            return
        }
        binding.apply {
            editTextEmail.setText(sharedPref.getString(email, ""))
            editTextPass.setText(sharedPref.getString(pass, ""))
            buttonRegister.performClick()
        }
    }

    /**
     * Listener for input fields
     */
    inner class TextFieldValidation(private val view: View) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            when (view.id) {
                R.id.editTextEmail -> validateEmail()
                R.id.editTextPass -> isPasswordValid()
            }
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }
}