package com.example.myprofile.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myprofile.R

class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class ContactViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        companion object {
            fun from(parent: ViewGroup): ContactViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_contact, parent, false)
                return ContactViewHolder(view)
            }
        }
    }
}