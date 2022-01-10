package com.vadym.myprofile.ui.main

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.vadym.myprofile.base.BaseFragment
import com.vadym.myprofile.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val args: MainFragmentArgs by navArgs()
    private val viewModel: MainViewModel by viewModels()
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var pagerAdapter: MainViewPagerAdapter

    override fun initialize() {
        binding.apply {

            viewPager = mainPager
            tabLayout = mainTabs
        }
        viewModel.setEmail(args.email)
        pagerAdapter = MainViewPagerAdapter(this, viewModel.email.value!!)
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
