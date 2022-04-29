package com.vadym.myprofile.presentation.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.vadym.myprofile.app.base.BaseFragment
import com.vadym.myprofile.app.utils.ext.loadCircledImage
import com.vadym.myprofile.app.utils.ext.safeNavigation
import com.vadym.myprofile.databinding.FragmentProfileBinding
import com.vadym.myprofile.presentation.model.ProfileModel
import com.vadym.myprofile.presentation.ui.main.MainFragment
import com.vadym.myprofile.presentation.ui.main.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel by viewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    override fun setObservers() {
        viewModel.profile.observe(viewLifecycleOwner) {
            setViews(it)
        }
        lifecycleScope.launch {
            viewModel.eventsFlow.flowWithLifecycle(
                viewLifecycleOwner.lifecycle,
                Lifecycle.State.RESUMED
            ).collect {
                showToast(it)
            }
        }
    }

    private fun setViews(profile: ProfileModel) {
        binding.apply {
            ivUserPhoto.loadCircledImage(profile.urlPhoto)
            tvProfileName.text = profile.name
            tvProfileCareer.text = profile.career
        }
    }

    private fun setListeners() {
        binding.apply {
            btnViewContacts.setOnClickListener {
                navigateToContactFragment()
            }
            btnEditProfile.setOnClickListener {
                findNavController().safeNavigation(MainFragmentDirections.actionMainFragmentToEditFragment())
            }
        }
    }

    private fun navigateToContactFragment() {
        (parentFragment as MainFragment).swipeToContactsList()
    }
}