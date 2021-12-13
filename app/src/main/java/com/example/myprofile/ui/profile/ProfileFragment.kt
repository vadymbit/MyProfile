package com.example.myprofile.ui.profile

import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myprofile.R
import com.example.myprofile.base.BaseFragment
import com.example.myprofile.databinding.FragmentProfileBinding
import com.example.myprofile.ui.contacts.ContactsFragment
import com.example.myprofile.utils.EMAIL
import com.example.myprofile.utils.featureNavigationEnabled
import com.example.myprofile.utils.imagepreprocessing.loadCircledImage

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val args: ProfileFragmentArgs by navArgs()

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
        val email: String? = if (featureNavigationEnabled) {
            args.email.substringBeforeLast("@")
        } else {
            arguments?.getString(EMAIL)?.substringBeforeLast("@")
        }
        if (!email.isNullOrEmpty()) {
            binding.tvProfileName.text = getString(
                R.string.profile_name,
                email.substringBefore(".").replaceFirstChar { it.uppercase() },
                email.substringAfter(".").replaceFirstChar { it.uppercase() })
        }
    }

    private fun navigateToContactFragment() {
        if (featureNavigationEnabled) {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToContactsFragment())
        } else {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<ContactsFragment>(R.id.fragmentContainerView)
                addToBackStack(null)
            }
        }
    }
}