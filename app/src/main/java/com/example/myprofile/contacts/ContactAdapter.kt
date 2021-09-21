package com.example.myprofile.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myprofile.R
import com.example.myprofile.databinding.ItemContactBinding

class ContactAdapter :
    ListAdapter<Contact, ContactAdapter.ContactViewHolder>(ContactDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ContactViewHolder private constructor(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Bind contact name, career and photo to the item of recycler view
         */
        fun bind(contact: Contact) {
            binding.textViewContactCareer.text = contact.career
            binding.textViewContactName.text = contact.name
            binding.imageViewProfilePhoto.setImageResource(R.drawable.ic_person)
        }

        //Create instance of ContactViewHolder and init binding for RecyclerView
        companion object {
            fun from(parent: ViewGroup): ContactViewHolder {
                val binding =
                    ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ContactViewHolder(binding)
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