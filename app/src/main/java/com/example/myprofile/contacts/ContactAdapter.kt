package com.example.myprofile.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myprofile.data.Contact
import com.example.myprofile.databinding.ItemContactBinding
import com.example.myprofile.imagepreprocessing.loadCircledImage

class ContactAdapter(private val iContactClickListener: IContactClickListener) :
    ListAdapter<Contact, ContactAdapter.ContactViewHolder>(ContactDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder.from(parent, iContactClickListener)
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
        fun bind(contact: Contact) {
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
            fun from(parent: ViewGroup, iContactClickListener: IContactClickListener): ContactViewHolder {
                val binding =
                    ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ContactViewHolder(binding, iContactClickListener)
            }
        }
    }

    /**
     * Is a utility class that calculates the difference between two lists
     * and outputs a list of update operations that converts the first list into the second one.
     */
    class ContactDiffCallback : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.phoneNumber == newItem.phoneNumber
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }

    }
}