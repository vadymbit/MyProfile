package com.example.myprofile

import android.app.ActivityOptions
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
    fun goToMyProfile(view: View) {
        if (isValidate()) {
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
    private fun isValidate(): Boolean {
        return validateEmail() && validatePassword()
    }

    /**
     * Validate input email address
     *
     * @return True if email correct or is not empty
     */
    private fun validateEmail(): Boolean {
        if (binding.editTextEmail.text.toString().trim().isEmpty()) {
            binding.textInputEmail.error = "Required field!"
            binding.editTextEmail.requestFocus()
            return false
        } else if (!isValidEmail(binding.editTextEmail.text.toString())) {
            binding.textInputEmail.error = "Incorrect E-mail address!"
            binding.editTextEmail.requestFocus()
            return false
        } else {
            binding.textInputEmail.isErrorEnabled = false
        }
        return true
    }

    /**
     * Check if the email matches the pattern
     *
     * @return True if the email matches the pattern
     */
    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Validate input password
     *
     * @return True if length of password more than [MIN_PASS_LENGTH] and less than [MAX_PASS_LENGTH]
     */
    private fun validatePassword(): Boolean {
        when {
            binding.editTextPass.text.toString().trim().isEmpty() -> {
                binding.textInputPass.error = "Required field!"
                binding.editTextPass.requestFocus()
                return false
            }
            binding.editTextPass.text.toString().length < MIN_PASS_LENGTH -> {
                binding.textInputPass.error = "Password must be at least $MIN_PASS_LENGTH symbols!"
                binding.editTextPass.requestFocus()
                return false
            }
            binding.editTextPass.text.toString().length > MAX_PASS_LENGTH -> {
                binding.textInputPass.error =
                    "Password can't be more than $MAX_PASS_LENGTH symbols!"
                binding.editTextPass.requestFocus()
                return false
            }
            else -> {
                binding.textInputPass.isErrorEnabled = false
            }
        }
        return true
    }

    /**
     * Setup listeners for input fields email and password
     */
    private fun setupListeners() {
        binding.editTextEmail.addTextChangedListener(TextFieldValidation(binding.editTextEmail))
        binding.editTextPass.addTextChangedListener(TextFieldValidation(binding.editTextPass))
    }

    /**
     * Go to login activity
     */
    fun goToLoginActivity(view: View) {
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
        binding.editTextEmail.setText(sharedPref.getString(email, ""))
        binding.editTextPass.setText(sharedPref.getString(pass, ""))
        binding.buttonRegister.performClick()
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
                R.id.editTextPass -> validatePassword()
            }
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }
}