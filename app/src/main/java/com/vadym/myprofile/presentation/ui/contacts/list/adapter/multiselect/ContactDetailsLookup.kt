package com.vadym.myprofile.presentation.ui.contacts.list.adapter.multiselect

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.vadym.myprofile.presentation.model.ContactModel
import com.vadym.myprofile.presentation.ui.contacts.list.adapter.ContactViewHolder

class ContactDetailsLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<ContactModel>() {
    
    override fun getItemDetails(e: MotionEvent): ItemDetails<ContactModel>? {
        val view = recyclerView.findChildViewUnder(e.x, e.y)
        if (view != null) {
            return (recyclerView.getChildViewHolder(view) as ContactViewHolder).getItemDetails()
        }
        return null
    }
}