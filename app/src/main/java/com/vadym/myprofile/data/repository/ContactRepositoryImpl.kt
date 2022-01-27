package com.vadym.myprofile.data.repository

import com.vadym.myprofile.data.storage.contact.ContactStorage
import com.vadym.myprofile.domain.model.ContactModel
import com.vadym.myprofile.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(private val storage: ContactStorage) :
    ContactRepository {

    override suspend fun addContact(contact: ContactModel): Boolean {
        return storage.addContact(contact)
    }

    override suspend fun removeContact(contact: ContactModel): Boolean {
        return storage.removeContact(contact)
    }

    override suspend fun getUserContacts(): Flow<List<ContactModel>> {
        return storage.getUserContacts()
    }
}