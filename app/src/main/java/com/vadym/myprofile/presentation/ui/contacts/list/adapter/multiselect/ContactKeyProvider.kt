package com.vadym.myprofile.presentation.ui.contacts.list.adapter.multiselect

import androidx.recyclerview.selection.ItemKeyProvider
import com.vadym.myprofile.presentation.model.ContactModel
import com.vadym.myprofile.presentation.ui.contacts.list.adapter.ContactAdapter

class ContactKeyProvider(private val contactAdapter: ContactAdapter) :
    ItemKeyProvider<ContactModel>(SCOPE_CACHED) {

    override fun getKey(position: Int): ContactModel  = contactAdapter.currentList[position]

    override fun getPosition(key: ContactModel): Int  = contactAdapter.currentList.indexOfFirst { it == key }

}