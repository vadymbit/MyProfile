package com.example.myprofile.contacts

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.example.myprofile.R
import com.example.myprofile.data.Contact
import com.example.myprofile.databinding.FragmentContactAddDialogBinding
import com.example.myprofile.imagepreprocessing.loadCircledImage

class ContactAddDialogFragment(private val model: ContactViewModel) : DialogFragment() {
    private var _binding: FragmentContactAddDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var contactPhoto: String

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
            toolbar.apply {
                setNavigationIcon(R.drawable.ic_add_contact_close)
                setNavigationOnClickListener {
                    dismiss()
                }
            }
            buttonSave.setOnClickListener {
                saveNewContact()
                dismiss()
            }
            buttonAddPhoto.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                openGalleryForPhoto.launch(intent)
            }
        }
    }

    private val openGalleryForPhoto =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                contactPhoto = result.data!!.dataString!!
                binding.imageContactPhoto.loadCircledImage(contactPhoto)
            }
        }

    private fun saveNewContact() {
        binding.apply {
            model.addContact(
                Contact(
                    editTextUsername.text.toString(),
                    editTextCareer.text.toString(),
                    editTextPhone.text.toString().toLong(),
                    editTextEmail.text.toString(),
                    editTextAdress.text.toString(),
                    editTextBirthDate.text.toString(),
                    contactPhoto
                )
            )
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