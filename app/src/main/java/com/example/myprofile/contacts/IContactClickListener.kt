package com.example.myprofile.contacts

import com.example.myprofile.data.Contact

interface IContactClickListener {
    fun removeUser(contact: Contact)
}