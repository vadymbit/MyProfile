package com.vadym.myprofile.presentation.ui.contacts.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.vadym.myprofile.R
import com.vadym.myprofile.app.utils.ext.loadCircledImage
import com.vadym.myprofile.databinding.ItemContactBinding
import com.vadym.myprofile.presentation.model.ContactModel

class ContactViewHolder private constructor(
    private val binding: ItemContactBinding,
    private val iContactClickListener: ContactAdapter.IContactClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var currentContact: ContactModel

    fun bind(contact: ContactModel, isSelectionMode: Boolean, isContactSelected: Boolean) {
        currentContact = contact
        itemView.isActivated = isSelectionMode
        binding.apply {
            cbSelect.isVisible = isSelectionMode
            btnDeleteContact.isVisible = !isSelectionMode
            cbSelect.isChecked = isContactSelected
            tvContactCareer.text = contact.career
            tvContactName.text = contact.name
            tvContactName.setSingleLine()
            tvContactName.isSelected = true
            ivUserPhoto.loadCircledImage(contact.urlPhoto)
            setSharedTransitionsName()
            bindListeners()
        }
    }

    fun getItemDetails(): ItemDetailsLookup.ItemDetails<ContactModel> =
        object : ItemDetailsLookup.ItemDetails<ContactModel>() {
            override fun getPosition(): Int = adapterPosition
            override fun getSelectionKey(): ContactModel = currentContact
        }

    private fun bindListeners() {
        binding.apply {
            btnDeleteContact.setOnClickListener {
                iContactClickListener.removeUser(currentContact)
                Toast.makeText(
                    itemView.context,
                    itemView.context.getString(R.string.toast_contact_removed),
                    Toast.LENGTH_SHORT
                ).show()
            }
            root.setOnClickListener {
                iContactClickListener.onContactClick(
                    currentContact,
                    ivUserPhoto,
                    tvContactCareer,
                    tvContactName
                )
            }
            root.setOnLongClickListener { iContactClickListener.onContactLongClick() }
        }
    }

    private fun setSharedTransitionsName() {
        binding.apply {
            tvContactCareer.transitionName =
                itemView.context.getString(
                    R.string.detail_transition_career,
                    currentContact.id.toString()
                )
            tvContactName.transitionName =
                itemView.context.getString(
                    R.string.detail_transition_name,
                    currentContact.id.toString()
                )
            ivUserPhoto.transitionName =
                itemView.context.getString(
                    R.string.detail_transition_photo,
                    currentContact.id.toString()
                )
        }
    }

    //Create instance of ContactViewHolder and init binding for RecyclerView
    companion object {
        fun from(
            parent: ViewGroup, contactClickListener: ContactAdapter.IContactClickListener
        ): ContactViewHolder {
            return ContactViewHolder(
                ItemContactBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                contactClickListener
            )
        }
    }
}