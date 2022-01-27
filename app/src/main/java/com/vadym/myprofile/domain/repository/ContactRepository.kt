package com.vadym.myprofile.domain.repository

import com.vadym.myprofile.domain.model.ContactModel

interface ContactRepository {

    suspend fun addContact(contact: ContactModel): Boolean

    suspend fun removeContact(contact: ContactModel): Boolean

    suspend fun getUserContacts()
}