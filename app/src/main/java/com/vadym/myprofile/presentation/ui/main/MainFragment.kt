package com.vadym.myprofile.presentation.ui.main

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.vadym.myprofile.app.base.BaseFragment
import com.vadym.myprofile.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var pagerAdapter: MainViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewPager = mainPager
            tabLayout = mainTabs
        }
        pagerAdapter = MainViewPagerAdapter(this)
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Profile"
                1 -> tab.text = "Contacts"
            }
        }.attach()
    }

    fun swipeToContactsList() {
        tabLayout.selectTab(tabLayout.getTabAt(1))
    }
}
