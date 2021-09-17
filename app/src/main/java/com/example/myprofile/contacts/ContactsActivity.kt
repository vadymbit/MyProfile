package com.example.myprofile.contacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.myprofile.databinding.ActivityContactsBinding

class ContactsActivity : AppCompatActivity() {

    private lateinit var contactAdapter: ContactAdapter
    private lateinit var binding: ActivityContactsBinding
    val viewModel: ContactViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.contactList.adapter = ContactAdapter()
    }
}