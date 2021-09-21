package com.example.myprofile.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContactViewModel : ViewModel() {

    private val _contacts = MutableLiveData<List<Contact>>()
    val contacts: LiveData<List<Contact>>
        get() = _contacts

    init {
        _contacts.value = mutableListOf(
            Contact("Alex", "Teacher", 12345678910),
            Contact("John", "Researcher", 3212346234),
            Contact("Gaben", "Designer", 648555555555),
            Contact("Richard", "Driver", 54765435675),
            Contact("Richard", "Driver", 54765435675),
            Contact("Richard", "Driver", 54765435675),
            Contact("Richard", "Driver", 54765435675),
            Contact("Richard", "Driver", 54765435675),
            Contact("Richard", "Driver", 54765435675),
            Contact("Richard", "Driver", 54765435675),
            Contact("Richard", "Driver", 54765435675),
            Contact("Richard", "Driver", 54765435675),
            Contact("Richard", "Driver", 54765435675),
            Contact("Richard", "Driver", 54765435675),
            Contact("Richard", "Driver", 54765435675),
            Contact("Richard", "Driver", 54765435675),
            Contact("Richard", "Driver", 54765435675),
            Contact("Nick", "Developer", 32897654675)
        )
    }

}