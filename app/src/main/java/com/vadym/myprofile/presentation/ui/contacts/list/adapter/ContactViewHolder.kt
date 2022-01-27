package com.vadym.myprofile.presentation.ui.contacts.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.vadym.myprofile.R
import com.vadym.myprofile.app.utils.ext.loadCircledImage
import com.vadym.myprofile.databinding.ItemContactBinding
import com.vadym.myprofile.domain.model.ContactModel

class ContactViewHolder private constructor(
    private val binding: ItemContactBinding,
    private val iContactClickListener: ContactAdapter.IContactClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var currentContact: ContactModel

    /**
     * Bind contact name, career and photo to the item of recycler view
     */
    fun bind(contact: ContactModel, selectionTracker: SelectionTracker<ContactModel>) {
        currentContact = contact
        itemView.isActivated = selectionTracker.hasSelection()
        binding.apply {
            if (selectionTracker.hasSelection()) {
                cbSelect.visibility = View.VISIBLE
                btnDeleteContact.visibility = View.INVISIBLE
            } else {
                cbSelect.visibility = View.INVISIBLE
                btnDeleteContact.visibility = View.VISIBLE
            }
            cbSelect.isChecked = selectionTracker.isSelected(contact)
            tvContactCareer.text = contact.career
            tvContactName.text = contact.name
            ivProfilePhoto.loadCircledImage(contact.urlPhoto)
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
                Toast.makeText(itemView.context, "Contact has been removed", Toast.LENGTH_SHORT)
                    .show()
            }
            root.setOnClickListener {
                iContactClickListener.onContactClick(
                    currentContact,
                    ivProfilePhoto,
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
            ivProfilePhoto.transitionName =
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