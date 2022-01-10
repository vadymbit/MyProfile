package com.vadym.myprofile.ui.contacts.contactsList.adapter

import android.view.ViewGroup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.ListAdapter
import com.vadym.myprofile.model.ContactModel

class ContactAdapter(
    private val contactClickListener: IContactClickListener
) :
    ListAdapter<ContactModel, ContactViewHolder>(ContactDiffCallback()) {

    lateinit var selectionTracker: SelectionTracker<ContactModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder.from(parent, contactClickListener)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, selectionTracker)
    }
}