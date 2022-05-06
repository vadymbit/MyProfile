package com.vadym.myprofile.presentation.ui.profile.edit

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vadym.myprofile.R
import com.vadym.myprofile.app.base.BaseFragment
import com.vadym.myprofile.app.utils.Constants.DATE_FORMAT
import com.vadym.myprofile.app.utils.Constants.GALLERY_LAUNCH_INPUT
import com.vadym.myprofile.app.utils.Constants.SCHEME_PACKAGE
import com.vadym.myprofile.app.utils.FileHelper.getPathFromURI
import com.vadym.myprofile.app.utils.ext.*
import com.vadym.myprofile.databinding.FragmentEditBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditFragment : BaseFragment<FragmentEditBinding>(FragmentEditBinding::inflate) {
    private val viewModel: EditViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        binding.etBirthDate.transformIntoDatePicker(requireContext(), DATE_FORMAT)
    }

    override fun setObservers() {
        viewModel.profilePhoto.observe(viewLifecycleOwner) {
            binding.ivUserPhoto.loadCircledImage(it)
        }
        viewModel.profile.observe(viewLifecycleOwner) {
            binding.apply {
                etUsername.setText(it.name)
                etCareer.setText(it.career)
                etPhone.setText(it.phoneNumber)
                etAddress.setText(it.address)
                etBirthDate.setText(it.birthDate)
                etInstagram.setText(it.instagram)
                etLinkedin.setText(it.linkedin)
                etTwitter.setText(it.twitter)
                etFacebook.setText(it.facebook)
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            btnAddPhoto.setOnClickListener {
                checkPermissions()
            }
            btnSave.setOnClickListener {
                if (isInputValid()) {
                    viewModel.saveProfile(
                        etUsername.text.toString(),
                        etCareer.text?.toString(),
                        etPhone.text.toString(),
                        etAddress.text?.toString(),
                        etBirthDate.text?.toString(),
                        etFacebook.text?.toString(),
                        etInstagram.text?.toString(),
                        etTwitter.text?.toString(),
                        etLinkedin.text?.toString()
                    )
                    if (findNavController().previousBackStackEntry?.destination?.id == R.id.registerFragment) {
                        goToMyProfile()
                    } else {
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

    private fun goToMyProfile() {
        findNavController().safeNavigation(EditFragmentDirections.actionEditFragmentToMainActivity())
        activity?.finish()
    }

    private fun isInputValid(): Boolean {
        binding.apply {
            validateInputFields()
            return viewModel.isInputValid(
                tiUsername.isErrorEnabled,
                tiPhone.isErrorEnabled
            )
        }
    }

    private fun validateInputFields() {
        binding.apply {
            tiPhone.validatePhoneNumber()
            tiUsername.validateRequiredField()
        }
    }

    private fun checkPermissions() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                openGalleryForPhoto.launch(GALLERY_LAUNCH_INPUT)
            }
            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                requireContext().showSnackbar(
                    getString(R.string.contact_add_denied_camera_permissions),
                    binding,
                    getString(R.string.edit_action_settings)
                ) { goToAppSettings() }
            }
            else -> requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            when {
                isGranted -> {
                    openGalleryForPhoto.launch(GALLERY_LAUNCH_INPUT)
                }
                else -> {
                    requireContext().showToast(getString(R.string.edit_why_need_permissions))
                }
            }
        }

    private val openGalleryForPhoto =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                viewModel.setProfilePhoto(photoPath = getPathFromURI(requireContext(), uri))
                binding.ivUserPhoto.loadCircledImage(uri)
            }
        }

    private fun goToAppSettings() {
        val appSettingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts(SCHEME_PACKAGE, requireContext().packageName, null)
        }
        startActivity(appSettingsIntent)
    }
}