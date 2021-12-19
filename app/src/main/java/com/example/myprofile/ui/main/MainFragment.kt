package com.example.myprofile.ui.main

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.myprofile.base.BaseFragment
import com.example.myprofile.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewPager by lazy { binding.mainPager }
    private val pagerAdapter by lazy { ViewPagerAdapter(this, viewModel.email.value!!) }
    private val tabLayout by lazy { binding.mainTabs }
    private val viewModel: MainViewModel by viewModels()
    private val args: MainFragmentArgs by navArgs()

    override fun initialize() {
        viewModel.setEmail(args.email)
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Profile"
                1 -> tab.text = "Contacts"
            }
        }.attach()
    }
}