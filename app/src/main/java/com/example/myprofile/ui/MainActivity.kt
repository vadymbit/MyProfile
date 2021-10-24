package com.example.myprofile.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myprofile.R
import com.example.myprofile.ui.contacts.ContactsActivity
import com.example.myprofile.databinding.ActivityMainBinding
import com.example.myprofile.utils.imagepreprocessing.loadCircledImage

private const val EMAIL = "com.example.myprofile.ui.EMAIL"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setName()
        binding.buttonViewContacts.setOnClickListener {
            startActivity(Intent(this, ContactsActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        binding.imageViewProfilePhoto.loadCircledImage(R.drawable.ic_person)
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