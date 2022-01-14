package com.vadym.myprofile.ui.contacts.contactsList.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.vadym.myprofile.R
import com.vadym.myprofile.databinding.ItemContactBinding
import com.vadym.myprofile.domain.model.ContactModel
import com.vadym.myprofile.utils.imagepreprocessing.loadCircledImage

class ContactViewHolder private constructor(
    private val binding: ItemContactBinding,
    private val iContactClickListener: IContactClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var currentContact: ContactModel
    /**
     * Bind contact name, career and photo to the item of recycler view
     */
    fun bind(contact: ContactModel, selectionTracker: SelectionTracker<ContactModel>) {
        currentContact = contact
        val context = itemView.context
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
            setSharedTransitionsName(contact, context)
            bindListeners(contact, context)
        }
    }

    fun getItemDetails(): ItemDetailsLookup.ItemDetails<ContactModel> =
        object : ItemDetailsLookup.ItemDetails<ContactModel>() {
            override fun getPosition(): Int = adapterPosition
            override fun getSelectionKey(): ContactModel = currentContact
        }

    private fun bindListeners(contact: ContactModel, context: Context) {
        binding.apply {
            btnDeleteContact.setOnClickListener {
                iContactClickListener.removeUser(contact)
                Toast.makeText(context, "Contact has been removed", Toast.LENGTH_SHORT).show()
            }
            root.setOnClickListener {
                iContactClickListener.onContactClick(
                    contact,
                    ivProfilePhoto,
                    tvContactCareer,
                    tvContactName
                )
            }
            root.setOnLongClickListener { iContactClickListener.onContactLongClick() }
        }
    }

    private fun setSharedTransitionsName(contact: ContactModel, context: Context) {
        binding.apply {
            tvContactCareer.transitionName =
                context.getString(R.string.detail_transition_career, contact.id.toString())
            tvContactName.transitionName =
                context.getString(R.string.detail_transition_name, contact.id.toString())
            ivProfilePhoto.transitionName =
                context.getString(R.string.detail_transition_photo, contact.id.toString())
        }
    }

    //Create instance of ContactViewHolder and init binding for RecyclerView
    companion object {
        fun from(
            parent: ViewGroup, contactClickListener: IContactClickListener
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