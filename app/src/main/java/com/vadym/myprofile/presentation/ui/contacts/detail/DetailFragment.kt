package com.vadym.myprofile.presentation.ui.contacts.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.vadym.myprofile.R
import com.vadym.myprofile.app.base.BaseFragment
import com.vadym.myprofile.app.utils.AppIntentHelper
import com.vadym.myprofile.app.utils.Constants
import com.vadym.myprofile.app.utils.Constants.PACKAGE_INSTAGRAM
import com.vadym.myprofile.app.utils.ext.loadCircledImage
import com.vadym.myprofile.databinding.FragmentDetailBinding
import com.vadym.myprofile.presentation.model.ContactModel

class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        setSharedTransitionsName(args.contactModel)
        setViews()
        setListeners()
    }

    private fun setViews() {
        binding.apply {
            tvProfileName.text = args.contactModel?.name
            tvProfileCareer.text = args.contactModel?.career
            tvProfileAddress.text = args.contactModel?.address
            ivUserPhoto.loadCircledImage(args.contactModel?.urlPhoto)
        }
    }

    private fun setListeners() {
        binding.apply {
            btnSocialTwitter.setOnClickListener {
                val intent = AppIntentHelper.getAppIntent(
                    context = requireContext(),
                    appPackage = Constants.PACKAGE_TWITTER,
                    appUri = getString(R.string.socials_twitter_app, args.contactModel?.twitter),
                    browserUri = getString(
                        R.string.socials_twitter_browser,
                        args.contactModel?.twitter
                    )
                )
                startActivity(intent)
            }
            btnSocialFacebook.setOnClickListener {
                val intent = AppIntentHelper.getAppIntent(
                    context = requireContext(),
                    appPackage = Constants.PACKAGE_FACEBOOK,
                    appUri = getString(R.string.socials_facebook_app, args.contactModel?.facebook),
                    browserUri = getString(
                        R.string.socials_facebook_browser,
                        args.contactModel?.facebook
                    )
                )
                startActivity(intent)
            }
            btnSocialLinkedin.setOnClickListener {
                val intent = AppIntentHelper.getAppIntent(
                    context = requireContext(),
                    appPackage = Constants.PACKAGE_LINKEDIN,
                    appUri = getString(R.string.socials_linkedin_app, args.contactModel?.linkedin),
                    browserUri = getString(
                        R.string.socials_linkedin_browser,
                        args.contactModel?.linkedin
                    )
                )
                startActivity(intent)
            }
            btnSocialInstagram.setOnClickListener {
                val intent = AppIntentHelper.getAppIntent(
                    context = requireContext(),
                    appPackage = PACKAGE_INSTAGRAM,
                    appUri = getString(
                        R.string.socials_instagram_app,
                        args.contactModel?.instagram
                    ),
                    browserUri = getString(
                        R.string.socials_instagram_browser,
                        args.contactModel?.instagram
                    )
                )
                startActivity(intent)
            }
        }
    }

    private fun setSharedTransitionsName(contact: ContactModel?) {
        binding.apply {
            ivUserPhoto.transitionName =
                getString(R.string.detail_transition_photo, contact?.id.toString())
            tvProfileCareer.transitionName =
                getString(R.string.detail_transition_career, contact?.id.toString())
            tvProfileName.transitionName =
                getString(R.string.detail_transition_name, contact?.id.toString())
        }
    }
}