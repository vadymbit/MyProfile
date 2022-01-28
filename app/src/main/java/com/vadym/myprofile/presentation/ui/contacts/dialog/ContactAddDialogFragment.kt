package com.vadym.myprofile.presentation.ui.contacts.dialog

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.vadym.myprofile.R
import com.vadym.myprofile.app.base.BaseDialogFragment
import com.vadym.myprofile.databinding.FragmentContactAddDialogBinding
import com.vadym.myprofile.app.utils.Constants.PHOTO_URI
import com.vadym.myprofile.app.utils.ext.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactAddDialogFragment :
    BaseDialogFragment<FragmentContactAddDialogBinding>(FragmentContactAddDialogBinding::inflate) {
    private val viewModel: ContactAddViewModel by viewModels()
    private val args by navArgs<ContactAddDialogFragmentArgs>()
    private var contactPhoto: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            isCancelable = false
            if (savedInstanceState != null) {
                ivContactPhoto.loadCircledImage(savedInstanceState.getString(PHOTO_URI))
            }
            setToolbar()
            setListeners()
            etBirthDate.transformIntoDatePicker(requireContext(), "MM/dd/yyyy")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contactPhoto?.let {
            outState.putString(PHOTO_URI, it)
        }
    }

    private fun isInputValid(): Boolean {
        binding.apply {
            validateInputFields()
            return viewModel.isInputValid(
                tiAddress.isErrorEnabled,
                tiUsername.isErrorEnabled,
                tiEmail.isErrorEnabled,
                tiPhone.isErrorEnabled
            )
        }
    }

    private fun setListeners() {
        binding.apply {
            btnSave.setOnClickListener {
                if (isInputValid()) {
                    viewModel.saveNewContact(
                        args.contactsListSize.toLong(),
                        etUsername.text.toString(),
                        etCareer.text?.toString(),
                        etPhone.text.toString().toLong(),
                        etEmail.text.toString(),
                        etAddress.text?.toString(),
                        etBirthDate.text?.toString(),
                        contactPhoto
                    )
                    dismiss()
                }
            }
            btnAddPhoto.setOnClickListener {
                requestPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun validateInputFields() {
        binding.apply {
            tiEmail.validateEmail()
            tiPhone.validatePhoneNumber()
            tiUsername.validateRequiredField()
            tiAddress.validateRequiredField()
        }
    }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    openGalleryForPhoto.launch(intent)
                }
                else -> {
                    Toast.makeText(
                        context,
                        getString(R.string.contact_add_denied_camera_permissions),
                        Toast.LENGTH_SHORT
                    )
                }
            }
        }

    /**
     * Open gallery to select the photo for new contact
     */
    private val openGalleryForPhoto =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                contactPhoto = result.data?.dataString
                binding.ivContactPhoto.loadCircledImage(contactPhoto)
            }
        }

    private fun setToolbar() {
        binding.toolbar.apply {
            setNavigationIcon(R.drawable.ic_add_contact_close)
            setNavigationOnClickListener {
                dismiss()
            }
        }
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }
}
