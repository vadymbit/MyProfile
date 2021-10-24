package com.example.myprofile.ui.contacts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myprofile.model.ContactModel
import com.example.myprofile.databinding.ItemContactBinding
import com.example.myprofile.utils.ContactDiffCallback
import com.example.myprofile.utils.imagepreprocessing.loadCircledImage

class ContactAdapter(private val contactClickListener: IContactClickListener) :
    ListAdapter<ContactModel, ContactAdapter.ContactViewHolder>(ContactDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder.from(parent, contactClickListener)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ContactViewHolder private constructor(
        private val binding: ItemContactBinding,
        private val iContactClickListener: IContactClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Bind contact name, career and photo to the item of recycler view
         */
        fun bind(contact: ContactModel) {
            binding.apply {
                textViewContactCareer.text = contact.career
                textViewContactName.text = contact.name
                imageViewProfilePhoto.loadCircledImage(contact.urlPhoto)
                buttonDeleteContact.setOnClickListener {
                    iContactClickListener.removeUser(contact)
                    val context = buttonDeleteContact.context
                    Toast.makeText(context, "Contact has been removed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        //Create instance of ContactViewHolder and init binding for RecyclerView
        companion object {
            fun from(parent: ViewGroup, contactClickListener: IContactClickListener): ContactViewHolder {
                val binding =
                    ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ContactViewHolder(binding, contactClickListener)
            }
        }
    }
}