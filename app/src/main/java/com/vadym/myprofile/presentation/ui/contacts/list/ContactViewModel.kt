package com.vadym.myprofile.presentation.ui.contacts.list

import androidx.lifecycle.*
import com.vadym.myprofile.app.base.BaseViewModel
import com.vadym.myprofile.domain.useCase.contact.GetUserContactUseCase
import com.vadym.myprofile.domain.useCase.contact.RemoveContactUseCase
import com.vadym.myprofile.domain.useCase.contact.SearchByNameUseCase
import com.vadym.myprofile.presentation.model.ContactModel
import com.vadym.myprofile.presentation.model.mapper.ContactMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val getUserContactUseCase: GetUserContactUseCase,
    private val removeContactUseCase: RemoveContactUseCase,
    private val searchByNameUseCase: SearchByNameUseCase
) : BaseViewModel() {

    private val contacts: LiveData<List<ContactModel>> = liveData {
        isLoading.value = true
        emitSource(onFlowResult(getUserContactUseCase()))
        isLoading.value = false
    }.map { domainContactList ->
        domainContactList.map { ContactMapper.toContactModel(it) }
    }
    private val _contactsLiveData = MediatorLiveData<List<ContactModel>>()
    val contactsLiveData: LiveData<List<ContactModel>>
        get() = _contactsLiveData

    init {
        _contactsLiveData.addSource(contacts) {
            _contactsLiveData.value = it
        }
    }

    fun searchContact(contactName: String) {
        launch {
            isLoading.value = true
            val matchesContacts = onFlowResult(searchByNameUseCase(contactName))
            _contactsLiveData.addSource(matchesContacts) {
                _contactsLiveData.value = it.map { userModel ->
                    ContactMapper.toContactModel(userModel)
                }
            }
            isLoading.value = false
        }
    }

    fun removeContact(contact: ContactModel) {
        launch {
            val result = removeContactUseCase(contact.id)
            onResult(result)
        }
    }

    fun removeContacts(contacts: List<ContactModel>) {
        contacts.forEach {
            removeContact(it)
        }
    }
}