package com.example.myprofile.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.myprofile.model.ContactModel

class ContactDiffCallback : DiffUtil.ItemCallback<ContactModel>() {
    override fun areItemsTheSame(oldItem: ContactModel, newItem: ContactModel): Boolean {
        return oldItem.phoneNumber == newItem.phoneNumber
    }

    override fun areContentsTheSame(oldItem: ContactModel, newItem: ContactModel): Boolean {
        return oldItem == newItem
    }

}