package com.vadym.myprofile.presentation.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.vadym.myprofile.R
import com.vadym.myprofile.app.base.BaseFragment
import com.vadym.myprofile.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate), MenuProvider {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var pagerAdapter: MainViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initMenu()
        setViews()
        setPagerAdapter()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_logout -> {
                viewModel.logout()
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToAuthActivity())
                activity?.finish()
                true
            }
            else -> false
        }
    }

    private fun initMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner)
    }

    private fun setViews() {
        binding.apply {
            viewPager = mainPager
            tabLayout = mainTabs
        }
    }

    private fun setPagerAdapter() {
        pagerAdapter = MainViewPagerAdapter(this)
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.pager_profile)
                1 -> tab.text = getString(R.string.pager_contacts)
            }
        }.attach()
    }

    fun swipeToContactsList() {
        tabLayout.selectTab(tabLayout.getTabAt(1))
    }
}
