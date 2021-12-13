package com.example.myprofile.ui.contacts

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.example.myprofile.R
import com.example.myprofile.databinding.FragmentContactAddDialogBinding
import com.example.myprofile.utils.PHOTO_URI
import com.example.myprofile.utils.imagepreprocessing.loadCircledImage
import com.example.myprofile.utils.ext.transformIntoDatePicker
import com.example.myprofile.utils.ext.validateEmail
import com.example.myprofile.utils.ext.validatePhoneNumber
import com.example.myprofile.utils.ext.validateRequiredField

class ContactAddDialogFragment : DialogFragment() {
    private var _binding: FragmentContactAddDialogBinding? = null
    private val binding get() = _binding!!
    private var contactPhoto: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        isCancelable = false
        _binding = FragmentContactAddDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            if (savedInstanceState != null) {
                ivContactPhoto.loadCircledImage(savedInstanceState.getString(PHOTO_URI))
            }
            setUpToolbar()
            setUpListeners()
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
            when {
                tiAddress.isErrorEnabled -> return false
                tiUsername.isErrorEnabled -> return false
                tiEmail.isErrorEnabled -> return false
                tiPhone.isErrorEnabled -> return false
            }
        }
        return true
    }

    private fun setUpListeners() {
        binding.apply {
            btnSave.setOnClickListener {
                if (isInputValid()) {
                    (parentFragment as ContactsFragment).saveNewContact(
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
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                openGalleryForPhoto.launch(intent)
            }
            setUpInputFields()
        }
    }

    private fun setUpInputFields() {
        binding.apply {
            tiEmail.validateEmail()
            tiPhone.validatePhoneNumber()
            tiUsername.validateRequiredField()
            tiAddress.validateRequiredField()
        }
    }

    /**
     * Open gallery to select the photo for new contact
     */
    private val openGalleryForPhoto =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                contactPhoto = result.data!!.dataString!!
                binding.ivContactPhoto.loadCircledImage(contactPhoto)
            }
        }

    private fun setUpToolbar() {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
