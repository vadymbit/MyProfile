package com.vadym.myprofile.presentation.ui.contacts.list

import androidx.lifecycle.*
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
    private val removeContactUseCase: RemoveContactUseCase,
) : BaseViewModel() {

    val contactsLiveData: LiveData<List<ContactModel>> = liveData {
        isLoading.value = true
        emitSource(onFlowResult(getUserContactUseCase()))
        isLoading.value = false
    }.map { list ->
        list.map { ContactMapper.toContactModel(it) }
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