package com.vadym.myprofile.presentation.ui.contacts.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.vadym.myprofile.R
import com.vadym.myprofile.app.base.BaseFragment
import com.vadym.myprofile.databinding.FragmentDetailBinding
import com.vadym.myprofile.domain.model.ContactModel
import com.vadym.myprofile.app.utils.ext.loadCircledImage

class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        binding.apply {
            setSharedTransitionsName(args.contactModel)
            tvProfileName.text = args.contactModel?.name
            tvProfileCareer.text = args.contactModel?.career
            tvProfileAddress.text = args.contactModel?.address
            ivProfilePhoto.loadCircledImage(args.contactModel?.urlPhoto)
        }
    }

    private fun setSharedTransitionsName(contact: ContactModel?) {
        binding.apply {
            ivProfilePhoto.transitionName =
                getString(R.string.detail_transition_photo, contact?.id.toString())
            tvProfileCareer.transitionName =
                getString(R.string.detail_transition_career, contact?.id.toString())
            tvProfileName.transitionName =
                getString(R.string.detail_transition_name, contact?.id.toString())
        }
    }

    private fun setToolbar() {
        binding.apply {
            toolbar.setNavigationIcon(R.drawable.ic_back)
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}