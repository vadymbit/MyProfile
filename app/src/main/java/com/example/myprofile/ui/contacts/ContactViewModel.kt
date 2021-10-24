package com.example.myprofile.ui.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myprofile.model.ContactModel

class ContactViewModel : ViewModel() {

    /**
     * The live data list in which will be stored all user contacts
     */
    private val _contacts = MutableLiveData<MutableList<ContactModel>>()
    val contacts: LiveData<MutableList<ContactModel>>
        get() = _contacts

    /**
     * Remove existing contact from contact live data list and notify it observers of changes
     *
     * @param contact The contact which will be removed from contact live data list
     */
    fun removeContact(contact: ContactModel) {
        _contacts.value?.remove(contact)
        _contacts.notifyObserver()
    }

    /**
     * Add new contact to contact live data list and notify it observers of changes
     *
     * @param contact The contact which will be added to contact live data list
     */
    fun addContact(contact: ContactModel) {
        _contacts.value?.add(contact)
        _contacts.notifyObserver()
    }

    /**
     * Function extensions created for collections saved in live data
     * When any changes in a collection you need to call this function
     * to notify observers. Value will be resigned by himself for update data version
     */
    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

    init {
        _contacts.value = mutableListOf(
            ContactModel("Alex", "Teacher", 12345678910, "empty", "", "", ""),
            ContactModel("John", "Researcher", 3212346234, "empty", "", "", ""),
            ContactModel("Gaben", "Designer", 648555555555, "empty", "", "", ""),
            ContactModel("Richard", "Driver", 54765435675, "empty", "", "", ""),
            ContactModel("Rick", "Actor", 342356745342, "empty", "", "", ""),
            ContactModel("Robert", "Architect", 12345643214, "empty", "", "", ""),
            ContactModel("John", "Driver", 87654365428, "empty", "", "", ""),
            ContactModel("Mary", "Driver", 12342132132, "empty", "", "", ""),
            ContactModel("Patricia", "Banker", 98778954321, "empty", "", "", ""),
            ContactModel("Jennifer", "Driver", 44563214351, "empty", "", "", ""),
            ContactModel("Michael", "Driver", 1231231233, "empty", "", "", ""),
            ContactModel("David", "Driver", 54365423451, "empty", "", "", ""),
            ContactModel("Joseph", "Accountant", 54365438765, "empty", "", "", ""),
            ContactModel("Charles", "Driver", 12398765432, "empty", "", "", ""),
            ContactModel("Daniel", "Driver", 65437656789, "empty", "", "", ""),
            ContactModel("Anthony", "Driver", 45674536542, "empty", "", "", ""),
            ContactModel("Mark", "Driver", 12345632456, "empty", "", "", ""),
            ContactModel("Nick", "Developer", 32897654675, "empty", "", "", "")
        )
    }

}