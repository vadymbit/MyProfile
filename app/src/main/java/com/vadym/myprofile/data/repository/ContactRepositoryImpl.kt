package com.vadym.myprofile.data.repository

import com.vadym.myprofile.data.storage.contact.ContactStorage
import com.vadym.myprofile.domain.model.ContactModel
import com.vadym.myprofile.domain.repository.ContactRepository

class ContactRepositoryImpl(private val storage: ContactStorage) : ContactRepository {

    override suspend fun addContact(contact: ContactModel): Boolean {
        return storage.addContact(contact)
    }

    override suspend fun removeContact(contact: ContactModel): Boolean {
        return storage.removeContact(contact)
    }

    override suspend fun getUserContacts() {
        storage.getUserContacts()
    }
}