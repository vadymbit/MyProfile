package com.vadym.myprofile.data.repository

import com.vadym.myprofile.data.model.ApiResult
import com.vadym.myprofile.data.model.UserDTO
import com.vadym.myprofile.data.model.mapper.UserDTOMapper
import com.vadym.myprofile.data.model.mapper.UserDomainMapper
import com.vadym.myprofile.data.source.local.AuthLocalStorage
import com.vadym.myprofile.data.source.local.ContactLocalStorage
import com.vadym.myprofile.data.source.remote.ApiService
import com.vadym.myprofile.domain.model.Result
import com.vadym.myprofile.domain.model.UserModel
import com.vadym.myprofile.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val storage: ContactLocalStorage,
    private val apiService: ApiService,
    private val profStorage: AuthLocalStorage
) : ContactRepository, BaseRepository() {

    override suspend fun addContact(contact: UserModel): Result<Boolean, Throwable> {
        val apiResult = getRequestResult { apiService.addContact(contactId = contact.id) }
        return getResult(apiResult) { storage.addContact(UserDomainMapper.toUserDB(contact)) }
    }

    override suspend fun removeContact(contactId: Int): Result<Boolean, Throwable> {
        val apiResult = getRequestResult { apiService.deleteContact(contactId) }
        return getResult(apiResult) { storage.removeContact(contactId) }
    }

    override suspend fun getAllUsers(): Result<List<UserModel>, Throwable> {
        val apiResult = getRequestResult { apiService.getAllUsers() }
        return getResult(apiResult) {
            (apiResult as ApiResult.Success).data.users.map {
                UserDTOMapper.toUserModel(it)
            }
        }
    }

    override suspend fun searchContactsByName(contactName: String): Result<Flow<List<UserModel>>, Throwable> {
        val searchedContacts = storage.searchContactsByName(contactName).map { usersDbList ->
            usersDbList.map {
                UserDomainMapper.toUserModel(it)
            }
        }
        return Result.Success(searchedContacts)
    }

    override suspend fun getUserContacts(): Result<Flow<List<UserModel>>, Throwable> {
        val apiResult = getRequestResult { apiService.getContacts() }
        return getFlowResult(
            apiResult = apiResult,
            saveNewData = { saveContactsToCache((apiResult as ApiResult.Success).data.contacts) },
            getCachedData = { getCachedUserContacts() }
        )
    }

    private suspend fun saveContactsToCache(contacts: List<UserDTO>) {
        storage.addContacts(contacts.map { userDTO ->
            UserDTOMapper.toUserDB(userDTO)
        })
    }

    private fun getCachedUserContacts(): Flow<List<UserModel>> {
        return storage.getUserContacts(profStorage.getProfileId())
            .map { usersDbList ->
                usersDbList.map { userDB ->
                    UserDomainMapper.toUserModel(userDB)
                }
            }
    }
}