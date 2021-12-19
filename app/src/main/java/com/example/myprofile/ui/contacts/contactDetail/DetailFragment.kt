package com.example.myprofile.ui.contacts.contactDetail

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.myprofile.R
import com.example.myprofile.base.BaseFragment
import com.example.myprofile.databinding.FragmentDetailBinding
import com.example.myprofile.model.ContactModel
import com.example.myprofile.utils.CONTACT_DATA
import com.example.myprofile.utils.featureNavigationEnabled
import com.example.myprofile.utils.imagepreprocessing.loadCircledImage

class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val args: DetailFragmentArgs by navArgs()

    override fun initialize() {
        setUpToolbar()
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        val contact: Bundle = initContactFromArgs()
        binding.apply {
            setSharedTransitionsName(contact.getParcelable(CONTACT_DATA)!!)
            tvProfileName.text = contact.getParcelable<ContactModel>(CONTACT_DATA)?.name
            tvProfileCareer.text = contact.getParcelable<ContactModel>(CONTACT_DATA)?.career
            tvProfileAddress.text = contact.getParcelable<ContactModel>(CONTACT_DATA)?.address
            ivProfilePhoto.loadCircledImage(contact.getParcelable<ContactModel>(CONTACT_DATA)?.urlPhoto)
        }
    }

    private fun initContactFromArgs(): Bundle {
        if (featureNavigationEnabled) {
            return args.toBundle()
        }
        return requireArguments()
    }

    private fun setSharedTransitionsName(contact: ContactModel) {
        binding.apply {
            ivProfilePhoto.transitionName =
                getString(R.string.detail_transition_photo, contact.id.toString())
            tvProfileCareer.transitionName =
                getString(R.string.detail_transition_career, contact.id.toString())
            tvProfileName.transitionName =
                getString(R.string.detail_transition_name, contact.id.toString())
        }
    }

    private fun setUpToolbar() {
        binding.apply {
            toolbar.setNavigationIcon(R.drawable.ic_back)
            toolbar.setNavigationOnClickListener {
                if (featureNavigationEnabled) {
                    val a = findNavController()
                    findNavController().popBackStack()
                } else {
                    parentFragmentManager.popBackStack()
                }
            }
        }
    }
}