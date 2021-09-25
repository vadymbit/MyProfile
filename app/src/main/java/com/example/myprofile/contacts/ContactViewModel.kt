package com.example.myprofile.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class ContactViewModel : ViewModel() {

    private val _contacts = MutableLiveData<List<Contact>>()
    val contacts: LiveData<List<Contact>>
        get() = _contacts

    init {
        _contacts.value = mutableListOf(
            Contact("Alex", "Teacher", 12345678910, "empty", "", ""),
            Contact("John", "Researcher", 3212346234, "empty", "", ""),
            Contact("Gaben", "Designer", 648555555555, "empty", "", ""),
            Contact("Richard", "Driver", 54765435675, "empty", "", ""),
            Contact("Rick", "Actor", 54765435675, "empty", "", ""),
            Contact("Robert", "Architect", 54765435675, "empty", "", ""),
            Contact("John", "Driver", 54765435675, "empty", "", ""),
            Contact("Mary", "Driver", 54765435675, "empty", "", ""),
            Contact("Patricia", "Banker", 54765435675, "empty", "", ""),
            Contact("Jennifer", "Driver", 54765435675, "empty", "", ""),
            Contact("Michael", "Driver", 54765435675, "empty", "", ""),
            Contact("David", "Driver", 54765435675, "empty", "", ""),
            Contact("Joseph", "Accountant", 54765435675, "empty", "", ""),
            Contact("Charles", "Driver", 54765435675, "empty", "", ""),
            Contact("Daniel", "Driver", 54765435675, "empty", "", ""),
            Contact("Anthony", "Driver", 54765435675, "empty", "", ""),
            Contact("Mark", "Driver", 54765435675, "empty", "", ""),
            Contact("Nick", "Developer", 32897654675, "empty", "", "")
        )
    }

}