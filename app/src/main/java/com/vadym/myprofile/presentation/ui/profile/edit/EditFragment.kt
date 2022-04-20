package com.vadym.myprofile.presentation.ui.profile.edit

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.vadym.myprofile.R
import com.vadym.myprofile.app.base.BaseFragment
import com.vadym.myprofile.app.utils.Constants
import com.vadym.myprofile.app.utils.FileHelper.getPathFromURI
import com.vadym.myprofile.app.utils.ext.*
import com.vadym.myprofile.databinding.FragmentEditBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditFragment : BaseFragment<FragmentEditBinding>(FragmentEditBinding::inflate) {
    private val viewModel: EditViewModel by viewModels()
    private var profilePhoto: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            if (savedInstanceState != null) {
                ivUserPhoto.loadCircledImage(savedInstanceState.getString(Constants.PHOTO_URI))
            }
            setListeners()
            etBirthDate.transformIntoDatePicker(requireContext(), "MM/dd/yyyy")
            requestPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        profilePhoto?.let {
            outState.putString(Constants.PHOTO_URI, it)
        }
    }

    private fun setListeners() {
        binding.apply {
            btnAddPhoto.setOnClickListener {
                when {
                    ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED -> {
                        openGalleryForPhoto.launch("image/*")
                    }
                    shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {

                    }
                    else -> requestPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
            btnSave.setOnClickListener {
                if (isInputValid()) {
                    viewModel.saveProfile(
                        etUsername.text.toString(),
                        etCareer.text?.toString(),
                        etPhone.text.toString(),
                        etAddress.text?.toString(),
                        etBirthDate.text?.toString(),
                        profilePhoto
                    )
                    if (findNavController().previousBackStackEntry?.destination?.id == R.id.authFragment) {
                        goToMyProfile()
                    } else {
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

    private fun setFields() {
        binding.apply {

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
                tiAddress.isErrorEnabled,
                tiUsername.isErrorEnabled,
                tiPhone.isErrorEnabled
            )
        }
    }

    private fun validateInputFields() {
        binding.apply {
            tiPhone.validatePhoneNumber()
            tiUsername.validateRequiredField()
            tiAddress.validateRequiredField()
        }
    }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            when {
                isGranted -> {
                    binding.btnAddPhoto.isClickable = true
                }
                else -> {
                    Snackbar.make(
                        binding.root,
                        R.string.contact_add_denied_camera_permissions,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }

    /**
     * Open gallery to select the photo for new contact
     */
    private val openGalleryForPhoto =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                profilePhoto = getPathFromURI(requireContext(), uri)
                binding.ivUserPhoto.loadCircledImage(uri)
            }
        }
}