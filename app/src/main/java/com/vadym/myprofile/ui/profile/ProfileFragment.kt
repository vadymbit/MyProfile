package com.vadym.myprofile.ui.profile

import com.vadym.myprofile.R
import com.vadym.myprofile.base.BaseFragment
import com.vadym.myprofile.databinding.FragmentProfileBinding
import com.vadym.myprofile.ui.main.MainFragment
import com.vadym.myprofile.utils.imagepreprocessing.loadCircledImage

class ProfileFragment(private val userEmail: String) : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    override fun initialize() {
        setName()
        binding.ivProfilePhoto.loadCircledImage(R.drawable.ic_person)
    }

    override fun setUpListeners() {
        binding.btnViewContacts.setOnClickListener {
            navigateToContactFragment()
        }
    }

    /**
     * Parse name of user from input email and set it in profile
     */
    private fun setName() {
        val email: String = userEmail.substringBeforeLast("@")
        binding.tvProfileName.text = getString(
                R.string.profile_name,
                email.substringBefore(".").replaceFirstChar { it.uppercase() },
                email.substringAfter(".").replaceFirstChar { it.uppercase() })
    }

    private fun navigateToContactFragment() {
        (parentFragment as MainFragment).swipeToContactsList()
    }
}