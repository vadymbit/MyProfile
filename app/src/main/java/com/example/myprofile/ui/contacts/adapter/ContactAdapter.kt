package com.example.myprofile.ui.contacts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myprofile.R
import com.example.myprofile.model.ContactModel
import com.example.myprofile.databinding.ItemContactBinding
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
            val context = itemView.context
            binding.apply {
                tvContactCareer.text = contact.career
                tvContactName.text = contact.name
                ivProfilePhoto.loadCircledImage(contact.urlPhoto)
                setSharedTransitionsName(contact, context)
                btnDeleteContact.setOnClickListener {
                    iContactClickListener.removeUser(contact)
                    Toast.makeText(context, "Contact has been removed", Toast.LENGTH_SHORT).show()
                }
                itemView.rootView.setOnClickListener {
                    iContactClickListener.navigateToContactDetail(
                        contact,
                        ivProfilePhoto,
                        tvContactCareer,
                        tvContactName
                    )
                }
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
}