package com.vadym.myprofile.presentation.ui.contacts.dialog.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.vadym.myprofile.presentation.model.ContactModel
import com.vadym.myprofile.presentation.ui.contacts.list.adapter.ContactDiffCallback

class ContactAddAdapter(
    private val contactAddClickListener: IContactAddClickListener
) :
    ListAdapter<ContactModel, ContactAddViewHolder>(ContactDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAddViewHolder {
        return ContactAddViewHolder.from(parent, contactAddClickListener)
    }

    override fun onBindViewHolder(holder: ContactAddViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface IContactAddClickListener {
        fun addContact(contact: ContactModel)
    }
}