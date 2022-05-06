package com.vadym.myprofile.presentation.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.vadym.myprofile.R
import com.vadym.myprofile.app.base.BaseFragment
import com.vadym.myprofile.app.utils.AppIntentHelper.getAppIntent
import com.vadym.myprofile.app.utils.Constants.PACKAGE_FACEBOOK
import com.vadym.myprofile.app.utils.Constants.PACKAGE_INSTAGRAM
import com.vadym.myprofile.app.utils.Constants.PACKAGE_LINKEDIN
import com.vadym.myprofile.app.utils.Constants.PACKAGE_TWITTER
import com.vadym.myprofile.app.utils.ext.loadCircledImage
import com.vadym.myprofile.app.utils.ext.safeNavigation
import com.vadym.myprofile.app.utils.ext.showToast
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
                requireContext().showToast(it)
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
            btnSocialTwitter.setOnClickListener {
                val intent = getAppIntent(
                    context = requireContext(),
                    appPackage = PACKAGE_TWITTER,
                    appUri = getString(R.string.socials_twitter_app, viewModel.profile.value?.twitter),
                    browserUri = getString(
                        R.string.socials_twitter_browser,
                        viewModel.profile.value?.twitter
                    )
                )
                startActivity(intent)
            }
            btnSocialFacebook.setOnClickListener {
                val intent = getAppIntent(
                    context = requireContext(),
                    appPackage = PACKAGE_FACEBOOK,
                    appUri = getString(R.string.socials_facebook_app, viewModel.profile.value?.facebook),
                    browserUri = getString(
                        R.string.socials_facebook_browser,
                        viewModel.profile.value?.facebook
                    )
                )
                startActivity(intent)
            }
            btnSocialLinkedin.setOnClickListener {
                val intent = getAppIntent(
                    context = requireContext(),
                    appPackage = PACKAGE_LINKEDIN,
                    appUri = getString(R.string.socials_linkedin_app, viewModel.profile.value?.linkedin),
                    browserUri = getString(
                        R.string.socials_linkedin_browser,
                        viewModel.profile.value?.linkedin
                    )
                )
                startActivity(intent)
            }
            btnSocialInstagram.setOnClickListener {
                val intent = getAppIntent(
                    context = requireContext(),
                    appPackage = PACKAGE_INSTAGRAM,
                    appUri = getString(
                        R.string.socials_instagram_app,
                        viewModel.profile.value?.instagram
                    ),
                    browserUri = getString(
                        R.string.socials_instagram_browser,
                        viewModel.profile.value?.instagram
                    )
                )
                startActivity(intent)
            }
        }
    }

    private fun navigateToContactFragment() {
        (parentFragment as MainFragment).swipeToContactsList()
    }
}