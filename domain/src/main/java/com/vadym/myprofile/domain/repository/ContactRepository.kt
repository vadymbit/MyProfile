package com.vadym.myprofile.domain.repository

import com.vadym.myprofile.domain.model.Result
import com.vadym.myprofile.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface ContactRepository {

    suspend fun addContact(contact: UserModel): Result<Boolean, Throwable>

    suspend fun removeContact(contactId: Int): Result<Boolean, Throwable>

    suspend fun getUserContacts(): Result<Flow<List<UserModel>>, Throwable>

    suspend fun getAllUsers(): Result<List<UserModel>, Throwable>

}