package com.example.myprofile.ui.contacts

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.example.myprofile.R
import com.example.myprofile.base.BaseFragment
import com.example.myprofile.databinding.FragmentContactsBinding
import com.example.myprofile.model.ContactModel
import com.example.myprofile.ui.contactDetail.DetailFragment
import com.example.myprofile.ui.contacts.adapter.ContactAdapter
import com.example.myprofile.ui.contacts.adapter.ContactItemDecoration
import com.example.myprofile.ui.contacts.adapter.IContactClickListener
import com.example.myprofile.utils.featureNavigationEnabled

class ContactsFragment : BaseFragment<FragmentContactsBinding>(FragmentContactsBinding::inflate),
    IContactClickListener {

    private val contactAdapter: ContactAdapter by lazy { ContactAdapter(contactClickListener = this) }
    private val viewModel: ContactViewModel by viewModels()

    override fun initialize() {
        setUpToolbar()
        postponeEnterTransition()
        initContactRecyclerView()
    }

    /**
     * Set recycler view adapter and item decoration
     */
    private fun initContactRecyclerView() {
        binding.apply {
            rvContactList.adapter = contactAdapter
            rvContactList.addItemDecoration(
                ContactItemDecoration(
                    resources.getDimensionPixelSize(
                        R.dimen.item_contact_margin
                    )
                )
            )
        }
    }

    override fun removeUser(contact: ContactModel) {
        viewModel.removeContact(contact)
    }

    override fun setUpListeners() {
        binding.apply {
            tvAddContact.setOnClickListener {
                ContactAddDialogFragment().show(childFragmentManager, null)
            }
            rvContactList.viewTreeObserver.addOnDrawListener {
                startPostponedEnterTransition()
            }
        }
    }

    override fun setUpObservers() {
        viewModel.contactsLiveData.observe(this, {
            contactAdapter.submitList(it.toMutableList())
        })
    }

    private fun setUpToolbar() {
        binding.toolbar.apply {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                if (featureNavigationEnabled) {
                    findNavController().popBackStack()
                } else {
                    parentFragmentManager.popBackStack()
                }
            }
        }
    }

    override fun navigateToContactDetail(
        contact: ContactModel,
        contactPhoto: ImageView,
        contactCareer: TextView,
        contactName: TextView
    ) {
        if (featureNavigationEnabled) {
            val action = ContactsFragmentDirections.actionContactsFragmentToDetailFragment(contact)
            findNavController()
                .navigate(
                    action,
                    FragmentNavigator.Extras.Builder()
                        .addSharedElements(
                            mapOf(
                                contactPhoto to contactPhoto.transitionName,
                                contactCareer to contactCareer.transitionName,
                                contactName to contactName.transitionName
                            )
                        ).build()
                )
        } else {
            val bundle = Bundle()
            bundle.putParcelable("Contact", contact)
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                addSharedElement(contactPhoto, contactPhoto.transitionName)
                addSharedElement(contactCareer, contactCareer.transitionName)
                addSharedElement(contactName, contactName.transitionName)
                replace<DetailFragment>(R.id.fragmentContainerView, null, bundle)
                addToBackStack(null)
            }
        }
    }

    /**
     * Create new contact from input fields in dialog fragment
     * and save it to the live data list of contacts
     */
    fun saveNewContact(
        username: String,
        career: String?,
        phone: Long,
        email: String,
        address: String?,
        birthDate: String?,
        uriPhoto: String?
    ) {
        viewModel.addContact(
            username,
            career,
            phone,
            email,
            address,
            birthDate,
            uriPhoto
        )
    }
}