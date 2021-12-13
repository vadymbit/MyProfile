package com.example.myprofile.ui.contacts.adapter

import android.widget.ImageView
import android.widget.TextView
import com.example.myprofile.model.ContactModel

interface IContactClickListener {
    fun removeUser(contact: ContactModel)
    fun navigateToContactDetail(
        contact: ContactModel,
        contactPhoto: ImageView,
        contactCareer: TextView,
        contactName: TextView
    )
}