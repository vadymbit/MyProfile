package com.vadym.myprofile.presentation.ui.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vadym.myprofile.presentation.ui.contacts.list.ContactsFragment
import com.vadym.myprofile.presentation.ui.profile.ProfileFragment
import com.vadym.myprofile.app.utils.Constants.NUM_TABS

class MainViewPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = NUM_TABS

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProfileFragment()
            1 -> ContactsFragment()
            else -> ProfileFragment()
        }
    }

}