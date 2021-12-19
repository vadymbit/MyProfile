package com.example.myprofile.ui.contacts.contactsList.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.myprofile.model.ContactModel

class ContactDiffCallback : DiffUtil.ItemCallback<ContactModel>() {
    override fun areItemsTheSame(oldItem: ContactModel, newItem: ContactModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ContactModel, newItem: ContactModel): Boolean {
        return oldItem == newItem
    }

}