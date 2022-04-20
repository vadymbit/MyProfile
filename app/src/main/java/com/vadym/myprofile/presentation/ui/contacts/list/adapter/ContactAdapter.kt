package com.vadym.myprofile.presentation.ui.contacts.list.adapter

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.ListAdapter
import com.vadym.myprofile.presentation.model.ContactModel

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

    interface IContactClickListener {
        fun removeUser(contact: ContactModel)
        fun onContactClick(
            contact: ContactModel,
            contactPhoto: ImageView,
            contactCareer: TextView,
            contactName: TextView
        )
        fun onContactLongClick(): Boolean
    }
}