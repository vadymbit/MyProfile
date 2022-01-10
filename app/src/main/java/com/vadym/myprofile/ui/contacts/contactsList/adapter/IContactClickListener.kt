package com.vadym.myprofile.ui.contacts.contactsList.adapter

import android.widget.ImageView
import android.widget.TextView
import com.vadym.myprofile.model.ContactModel

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