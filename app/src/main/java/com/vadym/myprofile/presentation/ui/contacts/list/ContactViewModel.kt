package com.vadym.myprofile.presentation.ui.contacts.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vadym.myprofile.domain.model.ContactModel

class ContactViewModel : ViewModel() {

    /**
     * The live data list in which will be stored all user contacts
     */
    private val _contactsLiveData = MutableLiveData<MutableList<ContactModel>>()
    val contactsLiveData: LiveData<MutableList<ContactModel>>
        get() = _contactsLiveData

    /**
     * Remove existing contact from contact live data list and notify it observers of changes
     *
     * @param contact The contact which will be removed from contact live data list
     */
    fun removeContact(contact: ContactModel) {
        _contactsLiveData.value?.remove(contact)
        _contactsLiveData.notifyObserver()
    }

    fun removeContacts(contacts: List<ContactModel>) {
        contacts.forEach {
            removeContact(it)
        }
    }

    /**
     * Add new contact to contact live data list and notify it observers of changes
     */
    fun addContact(
        username: String,
        career: String?,
        phone: Long,
        email: String,
        address: String?,
        birthDate: String?,
        uriPhoto: String?
    ) {
        val contact = ContactModel(
            _contactsLiveData.value?.size?.toLong() ?: 0,
            username,
            career,
            phone,
            email,
            address,
            birthDate,
            uriPhoto
        )
        _contactsLiveData.value?.add(contact)
        _contactsLiveData.notifyObserver()
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
        _contactsLiveData.value = mutableListOf(
            ContactModel(0, "Alex", "Teacher", 12345678910, "empty", "", "", ""),
            ContactModel(1, "John", "Researcher", 3212346234, "empty", "", "", ""),
            ContactModel(2, "Gaben", "Designer", 648555555555, "empty", "", "", ""),
            ContactModel(3, "Richard", "Driver", 54765435675, "empty", "", "", ""),
            ContactModel(4, "Rick", "Actor", 342356745342, "empty", "", "", ""),
            ContactModel(5, "Robert", "Architect", 12345643214, "empty", "", "", ""),
            ContactModel(6, "John", "Driver", 87654365428, "empty", "", "", ""),
            ContactModel(7, "Mary", "Driver", 12342132132, "empty", "", "", ""),
            ContactModel(8, "Patricia", "Banker", 98778954321, "empty", "", "", ""),
            ContactModel(9, "Jennifer", "Driver", 44563214351, "empty", "", "", ""),
            ContactModel(10, "Michael", "Driver", 1231231233, "empty", "", "", ""),
            ContactModel(11, "David", "Driver", 54365423451, "empty", "", "", ""),
            ContactModel(12, "Joseph", "Accountant", 54365438765, "empty", "", "", ""),
            ContactModel(13, "Charles", "Driver", 12398765432, "empty", "", "", ""),
            ContactModel(14, "Daniel", "Driver", 65437656789, "empty", "", "", ""),
            ContactModel(15, "Anthony", "Driver", 45674536542, "empty", "", "", ""),
            ContactModel(16, "Mark", "Driver", 12345632456, "empty", "", "", ""),
            ContactModel(17, "Nick", "Developer", 32897654675, "empty", "", "", "")
        )
    }

}