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
import com.google.android.material.snackbar.Snackbar
import com.vadym.myprofile.R
import com.vadym.myprofile.app.base.BaseFragment
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
        binding.etBirthDate.transformIntoDatePicker(requireContext(), "MM/dd/yyyy")
    }

    override fun setObservers() {
        viewModel.profilePhoto.observe(viewLifecycleOwner) {
            binding.ivUserPhoto.loadCircledImage(it)
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
                openGalleryForPhoto.launch("image/*")
            }
            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                Snackbar.make(
                    binding.root,
                    R.string.contact_add_denied_camera_permissions,
                    Snackbar.LENGTH_SHORT
                ).setAction("Settings") { goToAppSettings() }.show()
            }
            else -> requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            when {
                isGranted -> {
                    openGalleryForPhoto.launch("image/*")
                }
                else -> {
                    showToast("Need media permissions for loading photo")
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
            data = Uri.fromParts("package", requireContext().packageName, null)
        }
        startActivity(appSettingsIntent)
    }
}