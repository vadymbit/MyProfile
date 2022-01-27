package com.vadym.myprofile.presentation.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.vadym.myprofile.R
import com.vadym.myprofile.app.base.BaseFragment
import com.vadym.myprofile.databinding.FragmentProfileBinding
import com.vadym.myprofile.presentation.ui.main.MainFragment
import com.vadym.myprofile.app.utils.ext.loadCircledImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel by viewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        binding.ivProfilePhoto.loadCircledImage(R.drawable.ic_person)
    }

    private fun setListeners() {
        binding.btnViewContacts.setOnClickListener {
            navigateToContactFragment()
        }
    }

    private fun navigateToContactFragment() {
        (parentFragment as MainFragment).swipeToContactsList()
    }

    override fun setObservers() {
        viewModel.email.observe(this) { email ->
            val name: String = email.substringBeforeLast("@")
            binding.tvProfileName.text = getString(
                R.string.profile_name,
                name.substringBefore(".").replaceFirstChar { it.uppercase() },
                name.substringAfter(".").replaceFirstChar { it.uppercase() })
        }
    }
}