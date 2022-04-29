package com.vadym.myprofile.presentation.ui.contacts.dialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vadym.myprofile.app.utils.ext.loadCircledImage
import com.vadym.myprofile.databinding.ItemUserBinding
import com.vadym.myprofile.presentation.model.ContactModel

class ContactAddViewHolder private constructor(
    private val binding: ItemUserBinding,
    private val userClickListener: ContactAddAdapter.IContactAddClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(contact: ContactModel) {
        binding.apply {
            ivUserPhoto.loadCircledImage(contact.urlPhoto)
            tvContactName.text = contact.name
            tvContactName.setSingleLine()
            tvContactName.isSelected = true
            tvContactCareer.text = contact.career
            bindListeners(contact)
        }
    }

    private fun bindListeners(contact: ContactModel) {
        binding.btnAddContact.setOnClickListener { userClickListener.addContact(contact) }
    }

    companion object {
        fun from(
            parent: ViewGroup, userClickListener: ContactAddAdapter.IContactAddClickListener
        ): ContactAddViewHolder {
            return ContactAddViewHolder(
                ItemUserBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                userClickListener
            )
        }
    }
}