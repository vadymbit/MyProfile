package com.example.myprofile.ui.contacts.adapter

import com.example.myprofile.model.ContactModel

interface IContactClickListener {
    fun removeUser(contact: ContactModel)
}