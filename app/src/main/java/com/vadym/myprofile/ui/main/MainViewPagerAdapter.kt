package com.vadym.myprofile.ui.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vadym.myprofile.ui.contacts.contactsList.ContactsFragment
import com.vadym.myprofile.ui.profile.ProfileFragment
import com.vadym.myprofile.utils.NUM_TABS

class MainViewPagerAdapter(fragment: Fragment, private val email: String) :
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