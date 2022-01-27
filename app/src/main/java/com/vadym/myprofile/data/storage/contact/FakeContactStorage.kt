package com.vadym.myprofile.data.storage.contact

import com.vadym.myprofile.domain.model.ContactModel

class FakeContactStorage : ContactStorage {

    override suspend fun addContact(contact: ContactModel): Boolean {
        return contactList.add(contact)
    }
    override suspend fun removeContact(contact: ContactModel): Boolean {
        return contactList.remove(contact)
    }

    override suspend fun getUserContacts() {
        TODO("Not yet implemented")
    }

    private val contactList = mutableListOf(
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
