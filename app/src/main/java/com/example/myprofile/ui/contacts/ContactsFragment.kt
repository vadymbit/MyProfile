package com.example.myprofile.ui.contacts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.example.myprofile.R
import com.example.myprofile.base.BaseFragment
import com.example.myprofile.databinding.FragmentContactsBinding
import com.example.myprofile.model.ContactModel
import com.example.myprofile.ui.DetailFragment
import com.example.myprofile.ui.contacts.adapter.ContactAdapter
import com.example.myprofile.ui.contacts.adapter.ContactItemDecoration
import com.example.myprofile.ui.contacts.adapter.IContactClickListener
import com.example.myprofile.utils.featureNavigationEnabled
import com.example.myprofile.utils.waitForTransition

class ContactsFragment : BaseFragment<FragmentContactsBinding>(FragmentContactsBinding::inflate),
    IContactClickListener {

    private val contactAdapter: ContactAdapter by lazy { ContactAdapter(contactClickListener = this) }
    private val viewModel: ContactViewModel by viewModels()

    override fun initialize() {
        setUpToolbar()
        postponeEnterTransition()
        initContactRecyclerView()
        waitForTransition(binding.rvContactList)
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
            rvContactList.viewTreeObserver.addOnDrawListener {
                startPostponedEnterTransition()
            }
        }
    }

    override fun removeUser(contact: ContactModel) {
        viewModel.removeContact(contact)
    }

    override fun setUpListeners() {
        binding.tvAddContact.setOnClickListener {
            ContactAddDialogFragment().show(childFragmentManager, null)
        }
    }

    override fun setUpObservers() {
        viewModel.contacts.observe(this, {
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
        item: ContactModel,
        photoView: View,
        career: View,
        name: View
    ) {
        setSharedTransitionsName(item, photoView, career, name)
        if (featureNavigationEnabled) {
            val action = ContactsFragmentDirections.actionContactsFragmentToDetailFragment(item)
            findNavController()
                .navigate(
                    action,
                    FragmentNavigator.Extras.Builder()
                        .addSharedElements(
                            mapOf(
                                photoView to photoView.transitionName,
                                career to career.transitionName,
                                name to name.transitionName
                            )
                        ).build()
                )
        } else {
            val bundle = Bundle()
            bundle.putParcelable("Contact", item)
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                addSharedElement(photoView, photoView.transitionName)
                addSharedElement(career, career.transitionName)
                addSharedElement(name, name.transitionName)
                replace<DetailFragment>(R.id.fragmentContainerView, null, bundle)
                addToBackStack(null)
            }
        }
    }

    private fun setSharedTransitionsName(
        item: ContactModel,
        photoView: View,
        career: View,
        name: View
    ) {
        photoView.transitionName = getString(R.string.detail_transition_photo, item.id.toString())
        career.transitionName = getString(R.string.detail_transition_career, item.id.toString())
        name.transitionName = getString(R.string.detail_transition_name, item.id.toString())
    }

    /**
     * Create new contact from input fields in dialog fragment
     * and save it to the live data list of contacts
     */
    fun saveNewContact(
        username: String,
        career: String,
        phone: Long,
        email: String,
        address: String,
        birthDate: String,
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