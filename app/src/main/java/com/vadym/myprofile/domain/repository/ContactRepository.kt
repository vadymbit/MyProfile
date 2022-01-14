package com.vadym.myprofile.domain.repository

interface ContactRepository {

    fun addContact(): Boolean

    fun removeContact(): Boolean
}