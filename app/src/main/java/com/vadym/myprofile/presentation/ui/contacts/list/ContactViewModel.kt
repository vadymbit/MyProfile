package com.vadym.myprofile.presentation.ui.contacts.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.vadym.myprofile.app.base.BaseViewModel
import com.vadym.myprofile.domain.model.ContactModel
import com.vadym.myprofile.domain.useCase.contact.GetUserContactUseCase
import com.vadym.myprofile.domain.useCase.contact.RemoveContactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val getUserContactUseCase: GetUserContactUseCase,
    private val removeContactUseCase: RemoveContactUseCase
) : BaseViewModel() {

    val contactsLiveData: LiveData<List<ContactModel>> = liveData {
        emitSource(getUserContactUseCase().asLiveData())
    }

    /**
     * Remove existing contact from contact live data list and notify it observers of changes
     *
     * @param contact The contact which will be removed from contact live data list
     */
    fun removeContact(contact: ContactModel) {
        launch {
            removeContactUseCase(contact)
        }
    }

    fun removeContacts(contacts: List<ContactModel>) {
        contacts.forEach {
            removeContact(it)
        }
    }
}