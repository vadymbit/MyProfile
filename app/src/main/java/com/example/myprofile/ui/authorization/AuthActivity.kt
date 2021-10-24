package com.example.myprofile.ui.authorization

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.myprofile.R
import com.example.myprofile.databinding.ActivityAuthBinding
import com.example.myprofile.ui.MainActivity

/**
 * The name of the extra data, with package prefix for intent
 */
private const val EMAIL = "com.example.myprofile.ui.EMAIL"

private const val MIN_PASS_LENGTH = 8

private const val MAX_PASS_LENGTH = 16

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
            if (editTextEmail.text.isNullOrBlank()) {
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
                editTextPass.text.isNullOrBlank() -> {
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
            editTextEmail.doOnTextChanged { _, _, _, _ ->  validateEmail()}
            editTextPass.doOnTextChanged { _, _, _, _ ->  isPasswordValid()}
            buttonRegister.setOnClickListener { goToMyProfile() }
            textViewSignIn.setOnClickListener { goToLoginActivity() }
            buttonLoginViaSocial.setOnClickListener {
                Toast.makeText(
                    this@AuthActivity,
                    "Login via Google",
                    Toast.LENGTH_SHORT
                ).show()
            }
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
     * Go to profile activity if user saved
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
}