package com.example.myprofile.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myprofile.R
import com.example.myprofile.data.Contact
import com.example.myprofile.databinding.ItemContactBinding

class ContactAdapter(private val viewModel: ContactViewModel) :
    ListAdapter<Contact, ContactAdapter.ContactViewHolder>(ContactDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder.from(parent, viewModel)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ContactViewHolder private constructor(
        private val binding: ItemContactBinding,
        private val model: ContactViewModel
    ) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Bind contact name, career and photo to the item of recycler view
         */
        fun bind(contact: Contact) {
            binding.apply {
                textViewContactCareer.text = contact.career
                textViewContactName.text = contact.name
                imageViewProfilePhoto.setImageResource(R.drawable.ic_person)
                buttonDeleteContact.setOnClickListener {
                    model.deleteContact(contact)
                }
            }
        }

        //Create instance of ContactViewHolder and init binding for RecyclerView
        companion object {
            fun from(parent: ViewGroup, viewModel: ContactViewModel): ContactViewHolder {
                val binding =
                    ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ContactViewHolder(binding, viewModel)
            }
        }
    }

    class ContactDiffCallback : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.phoneNumber == newItem.phoneNumber
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }

    }
}