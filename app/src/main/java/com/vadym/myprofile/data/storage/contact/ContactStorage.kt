package com.vadym.myprofile.data.storage.contact

import com.vadym.myprofile.domain.model.ContactModel
import kotlinx.coroutines.flow.Flow

interface ContactStorage {

    suspend fun addContact(contact: ContactModel): Boolean

    suspend fun removeContact(contact: ContactModel): Boolean

    suspend fun getUserContacts(): Flow<List<ContactModel>>

}