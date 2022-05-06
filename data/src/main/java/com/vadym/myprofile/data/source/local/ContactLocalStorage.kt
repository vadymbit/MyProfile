package com.vadym.myprofile.data.source.local

import com.vadym.myprofile.data.model.UserDB
import kotlinx.coroutines.flow.Flow

interface ContactLocalStorage {

    suspend fun addContact(contact: UserDB): Boolean

    suspend fun removeContact(contactId: Int): Boolean

    suspend fun removeAll(): Boolean

    fun getUserContacts(profileId: Int): Flow<List<UserDB>>

    suspend fun addContacts(usersList: List<UserDB>)
}