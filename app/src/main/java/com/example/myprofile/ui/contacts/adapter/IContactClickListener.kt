package com.example.myprofile.ui.contacts.adapter

import android.view.View
import com.example.myprofile.model.ContactModel

interface IContactClickListener {
    fun removeUser(contact: ContactModel)
    fun navigateToContactDetail(
        item: ContactModel,
        photoView: View,
        career: View,
        name: View
    )
}