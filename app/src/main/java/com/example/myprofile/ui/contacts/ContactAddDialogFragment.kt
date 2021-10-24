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
import com.example.myprofile.utils.imagepreprocessing.loadCircledImage
import com.example.myprofile.utils.transformIntoDatePicker

private const val PHOTO_URI = "PHOTO_URI"

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
                imageContactPhoto.loadCircledImage(savedInstanceState.getString(PHOTO_URI))
            }
            setUpToolbar()
            editTextBirthDate.transformIntoDatePicker(requireContext(), "MM/dd/yyyy")
            buttonSave.setOnClickListener {
                if (isInputValid()) {
                    (activity as ContactsActivity).saveNewContact(
                        editTextUsername.text.toString(),
                        editTextCareer.text.toString(),
                        editTextPhone.text.toString().toLong(),
                        editTextEmail.text.toString(),
                        editTextAddress.text.toString(),
                        editTextBirthDate.text.toString(),
                        contactPhoto
                    )
                    dismiss()
                }
            }
            buttonAddPhoto.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                openGalleryForPhoto.launch(intent)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contactPhoto?.let {
            outState.putString(PHOTO_URI, it)
        }
    }

    /**
     * Open gallery to select the photo for new contact
     */
    private val openGalleryForPhoto =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                contactPhoto = result.data!!.dataString!!
                binding.imageContactPhoto.loadCircledImage(contactPhoto)
            }
        }

    private fun isInputValid(): Boolean {
        binding.apply {
            when {
                editTextUsername.text.isNullOrBlank() -> {
                    editTextUsername.error = getString(R.string.contact_add_required)
                    return false
                }
                editTextCareer.text.isNullOrBlank() -> {
                    editTextCareer.error = getString(R.string.contact_add_required)
                    return false
                }
                editTextPhone.text.isNullOrBlank() -> {
                    editTextPhone.error = getString(R.string.contact_add_required)
                    return false
                }
                editTextEmail.text.isNullOrBlank() -> {
                    editTextEmail.error = getString(R.string.contact_add_required)
                    return false
                }
                editTextAddress.text.isNullOrBlank() -> {
                    editTextAddress.error = getString(R.string.contact_add_required)
                    return false
                }
                editTextBirthDate.text.isNullOrBlank() -> {
                    editTextBirthDate.error = getString(R.string.contact_add_required)
                    return false
                }
            }
        }
        return true
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
