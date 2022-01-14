package com.vadym.myprofile.ui.contacts.contactsList.adapter.multiselect

import androidx.recyclerview.selection.ItemKeyProvider
import com.vadym.myprofile.domain.model.ContactModel
import com.vadym.myprofile.ui.contacts.contactsList.adapter.ContactAdapter

class ContactKeyProvider(private val contactAdapter: ContactAdapter) :
    ItemKeyProvider<ContactModel>(SCOPE_CACHED) {

    override fun getKey(position: Int): ContactModel  = contactAdapter.currentList[position]

    override fun getPosition(key: ContactModel): Int  = contactAdapter.currentList.indexOfFirst { it == key }

}