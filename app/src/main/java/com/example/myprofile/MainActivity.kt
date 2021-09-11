package com.example.myprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myprofile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setName()
    }

    /**
     * Parse name of user from input email and set it in profile
     */
    private fun setName() {
        val email = intent.getStringExtra(EMAIL)?.substringBeforeLast("@")
        if (!email.isNullOrEmpty()) {
            binding.textViewProfileName.text = getString(
                R.string.profile_name,
                email.substringBefore(".").replaceFirstChar { it.uppercase() },
                email.substringAfter(".").replaceFirstChar { it.uppercase() })
        }
    }

}