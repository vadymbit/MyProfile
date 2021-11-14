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

class ContactAdapter(
    private val contactClickListener: IContactClickListener
) :
    ListAdapter<ContactModel, ContactAdapter.ContactViewHolder>(ContactDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder.from(parent, contactClickListener)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.rootView.setOnClickListener {
            contactClickListener.navigateToContactDetail(
                item,
                holder.binding.ivProfilePhoto,
                holder.binding.tvContactCareer,
                holder.binding.tvContactName
            )
        }
        holder.bind(item)
    }

    class ContactViewHolder private constructor(
        val binding: ItemContactBinding,
        private val iContactClickListener: IContactClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Bind contact name, career and photo to the item of recycler view
         */
        fun bind(contact: ContactModel) {
            binding.apply {
                tvContactCareer.text = contact.career
                tvContactName.text = contact.name
                ivProfilePhoto.loadCircledImage(contact.urlPhoto)
                btnDeleteContact.setOnClickListener {
                    iContactClickListener.removeUser(contact)
                    val context = itemView.context
                    Toast.makeText(context, "Contact has been removed", Toast.LENGTH_SHORT).show()
                }
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
                    ), contactClickListener
                )
            }
        }
    }
}