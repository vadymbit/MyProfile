package com.vadym.myprofile.domain.repository

import com.vadym.myprofile.domain.model.ContactModel
import kotlinx.coroutines.flow.Flow

interface ContactRepository {

    suspend fun addContact(contact: ContactModel): Boolean

    suspend fun removeContact(contact: ContactModel): Boolean

    suspend fun getUserContacts(): Flow<List<ContactModel>>
}