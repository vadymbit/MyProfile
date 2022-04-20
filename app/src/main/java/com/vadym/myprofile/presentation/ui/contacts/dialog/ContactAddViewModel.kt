package com.vadym.myprofile.presentation.ui.contacts.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vadym.myprofile.app.base.BaseViewModel
import com.vadym.myprofile.domain.useCase.contact.AddContactUseCase
import com.vadym.myprofile.domain.useCase.contact.GetAllUsersUseCase
import com.vadym.myprofile.presentation.model.ContactModel
import com.vadym.myprofile.presentation.model.mapper.ContactMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactAddViewModel @Inject constructor(
    private val addContactUseCase: AddContactUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
) :
    BaseViewModel() {

    init {
        loadUsers()
    }
    private val _usersLiveData = MutableLiveData<List<ContactModel>>()
    val usersLiveData: LiveData<List<ContactModel>>
        get() = _usersLiveData

    fun addContact(contact: ContactModel) {
        launch {
            onResult(addContactUseCase(ContactMapper.toUserModel(contact)))
        }
    }

    private fun loadUsers() {
        launch {
            isLoading.value = true
            val userList = onResult(getAllUsersUseCase())?.map { ContactMapper.toContactModel(it) }
            userList?.let {
                _usersLiveData.value = it
            }
            isLoading.value = false
        }
    }
}