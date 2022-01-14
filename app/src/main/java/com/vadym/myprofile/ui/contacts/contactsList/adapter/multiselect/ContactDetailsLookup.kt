package com.vadym.myprofile.ui.contacts.contactsList.adapter.multiselect

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.vadym.myprofile.domain.model.ContactModel
import com.vadym.myprofile.ui.contacts.contactsList.adapter.ContactViewHolder

class ContactDetailsLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<ContactModel>() {
    
    override fun getItemDetails(e: MotionEvent): ItemDetails<ContactModel>? {
        val view = recyclerView.findChildViewUnder(e.x, e.y)
        if (view != null) {
            return (recyclerView.getChildViewHolder(view) as ContactViewHolder).getItemDetails()
        }
        return null
    }
}