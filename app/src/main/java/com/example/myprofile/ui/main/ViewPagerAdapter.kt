package com.example.myprofile.ui.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myprofile.ui.contacts.contactsList.ContactsFragment
import com.example.myprofile.ui.profile.ProfileFragment
import com.example.myprofile.utils.NUM_TABS

class ViewPagerAdapter(fragment: Fragment, private val email: String) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = NUM_TABS

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProfileFragment(email)
            1 -> ContactsFragment()
            else -> ProfileFragment(email)
        }
    }

}