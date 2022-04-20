package com.vadym.myprofile.data.source.local

import com.vadym.myprofile.data.model.UserDB
import com.vadym.myprofile.data.source.local.db.UsersDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersLocalStorageImpl @Inject constructor(private val db: UsersDao) : UsersLocalStorage {
    override suspend fun addContact(contact: UserDB): Boolean {
        db.addContact(contact)
        return true
    }

    override suspend fun removeContact(contactId: Int): Boolean {
        return db.deleteContact(contactId) > 0
    }

    override suspend fun removeAll(): Boolean {
        return db.deleteAll() > 0
    }

    override fun getUserContacts(profileId: Int): Flow<List<UserDB>> {
        return db.getAllUserContact(profileId)
    }

    override suspend fun addContacts(usersList: List<UserDB>) {
        db.deleteAll()
        db.addContacts(usersList)
    }
}
