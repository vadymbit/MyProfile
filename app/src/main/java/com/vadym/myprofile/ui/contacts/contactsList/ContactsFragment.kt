package com.vadym.myprofile.ui.contacts.contactsList

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import com.vadym.myprofile.R
import com.vadym.myprofile.base.BaseFragment
import com.vadym.myprofile.databinding.FragmentContactsBinding
import com.vadym.myprofile.model.ContactModel
import com.vadym.myprofile.ui.contacts.ContactAddDialogFragment
import com.vadym.myprofile.ui.contacts.contactsList.adapter.ContactAdapter
import com.vadym.myprofile.ui.contacts.contactsList.adapter.ContactItemDecoration
import com.vadym.myprofile.ui.contacts.contactsList.adapter.IContactClickListener
import com.vadym.myprofile.ui.contacts.contactsList.adapter.multiselect.ContactDetailsLookup
import com.vadym.myprofile.ui.contacts.contactsList.adapter.multiselect.ContactKeyProvider
import com.vadym.myprofile.ui.main.MainFragmentDirections

class ContactsFragment : BaseFragment<FragmentContactsBinding>(FragmentContactsBinding::inflate),
    IContactClickListener {

    private val contactAdapter: ContactAdapter by lazy { ContactAdapter(contactClickListener = this) }
    private val viewModel: ContactViewModel by viewModels()
    private lateinit var selectionTracker: SelectionTracker<ContactModel>
    private var actionMode: ActionMode? = null

    override fun initialize(savedInstanceState: Bundle?) {
        parentFragment?.postponeEnterTransition()
        initContactRecyclerView()
        initSelectionTracker()
        selectionTracker.onRestoreInstanceState(savedInstanceState)
    }

    private fun initSelectionTracker() {
        selectionTracker = SelectionTracker.Builder(
            "contact_tracker",
            binding.rvContactList,
            ContactKeyProvider(contactAdapter),
            ContactDetailsLookup(binding.rvContactList),
            StorageStrategy.createParcelableStorage(ContactModel::class.java)
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()
        contactAdapter.selectionTracker = selectionTracker
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::selectionTracker.isInitialized) {
            selectionTracker.onSaveInstanceState(outState)
        }
    }

    /**
     * Set recycler view adapter and item decoration
     */
    private fun initContactRecyclerView() {
        binding.apply {
            rvContactList.adapter = contactAdapter
            rvContactList.layoutManager = LinearLayoutManager(context)
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
                parentFragment?.startPostponedEnterTransition()
            }
            fabDeleteContacts.setOnClickListener { removeSelectedContacts() }
        }
    }

    private fun removeSelectedContacts() {
        viewModel.removeContacts(selectionTracker.selection.map { it })
    }

    override fun setUpObservers() {
        viewModel.contactsLiveData.observe(this, {
            contactAdapter.submitList(it.toMutableList())
        })
        selectionTracker.addObserver(object : SelectionTracker.SelectionObserver<ContactModel>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()
                if (!selectionTracker.hasSelection()) {
                    actionMode?.finish()
                    binding.fabDeleteContacts.hide()
                } else {
                    setSelectedTitle(selectionTracker.selection.size())
                }
            }
        })
    }

    override fun onContactClick(
        contact: ContactModel,
        contactPhoto: ImageView,
        contactCareer: TextView,
        contactName: TextView
    ) {
        navigateToContactDetail(
            contact,
            contactPhoto,
            contactCareer,
            contactName
        )
    }

    private fun navigateToContactDetail(
        contact: ContactModel,
        contactPhoto: ImageView,
        contactCareer: TextView,
        contactName: TextView
    ) {
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(contact)
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
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onContactLongClick(): Boolean {
        actionMode = activity?.startActionMode(
            object : ActionMode.Callback {
                override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    binding.fabDeleteContacts.show()
                    contactAdapter.notifyDataSetChanged()
                    return true
                }

                override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = true

                override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean = true

                override fun onDestroyActionMode(mode: ActionMode?) {
                    selectionTracker.clearSelection()
                    binding.rvContactList.itemAnimator?.isRunning {
                        Log.d("Contact", "Animation end")
                        contactAdapter.notifyDataSetChanged()
                    }
                }
            }
        )
        return true
    }

    private fun setSelectedTitle(selected: Int) {
        actionMode?.title = "Selected: $selected"
    }

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