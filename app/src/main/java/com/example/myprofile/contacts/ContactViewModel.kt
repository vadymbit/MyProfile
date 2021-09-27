package com.example.myprofile.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myprofile.data.Contact

class ContactViewModel : ViewModel() {

    private val _contacts = MutableLiveData<MutableList<Contact>>()
    val contacts: LiveData<MutableList<Contact>>
        get() = _contacts
    
    fun deleteContact(contact: Contact) {
        _contacts.value?.remove(contact)
        _contacts.notifyObserver()
    }

    fun addContact(contact: Contact) {
        _contacts.value?.add(contact)
        _contacts.notifyObserver()
    }

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

    init {
        _contacts.value = mutableListOf(
            Contact("Alex", "Teacher", 12345678910, "empty", "", ""),
            Contact("John", "Researcher", 3212346234, "empty", "", ""),
            Contact("Gaben", "Designer", 648555555555, "empty", "", ""),
            Contact("Richard", "Driver", 54765435675, "empty", "", ""),
            Contact("Rick", "Actor", 342356745342, "empty", "", ""),
            Contact("Robert", "Architect", 12345643214, "empty", "", ""),
            Contact("John", "Driver", 87654365428, "empty", "", ""),
            Contact("Mary", "Driver", 12342132132, "empty", "", ""),
            Contact("Patricia", "Banker", 98778954321, "empty", "", ""),
            Contact("Jennifer", "Driver", 44563214351, "empty", "", ""),
            Contact("Michael", "Driver", 1231231233, "empty", "", ""),
            Contact("David", "Driver", 54365423451, "empty", "", ""),
            Contact("Joseph", "Accountant", 54365438765, "empty", "", ""),
            Contact("Charles", "Driver", 12398765432, "empty", "", ""),
            Contact("Daniel", "Driver", 65437656789, "empty", "", ""),
            Contact("Anthony", "Driver", 45674536542, "empty", "", ""),
            Contact("Mark", "Driver", 12345632456, "empty", "", ""),
            Contact("Nick", "Developer", 32897654675, "empty", "", "")
        )
    }

}