package com.vadym.myprofile.presentation.ui.contacts.list

import androidx.lifecycle.*
import androidx.recyclerview.selection.Selection
import com.vadym.myprofile.app.base.BaseViewModel
import com.vadym.myprofile.domain.useCase.contact.GetUserContactUseCase
import com.vadym.myprofile.domain.useCase.contact.RemoveContactUseCase
import com.vadym.myprofile.presentation.model.ContactModel
import com.vadym.myprofile.presentation.model.mapper.ContactMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val getUserContactUseCase: GetUserContactUseCase,
    private val removeContactUseCase: RemoveContactUseCase
) : BaseViewModel() {

    val contacts: LiveData<List<ContactModel>> = liveData {
        isLoading.value = true
        emitSource(onFlowResult(getUserContactUseCase()))
        isLoading.value = false
    }.map { domainContactList ->
        domainContactList.map { ContactMapper.toContactModel(it) }
    }

    fun searchContact(contactName: String): List<ContactModel> {
        return contacts.value?.filter { contact ->
            contact.name.contains(contactName, ignoreCase = true)
        } ?: emptyList()
    }

    fun removeContact(contact: ContactModel) {
        launch {
            val result = removeContactUseCase(contact.id)
            onResult(result)
        }
    }

    fun removeContacts(contacts: Selection<ContactModel>) {
        contacts.forEach {
            removeContact(it)
        }
    }
}