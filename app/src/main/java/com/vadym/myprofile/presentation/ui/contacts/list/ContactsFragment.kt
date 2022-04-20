package com.vadym.myprofile.presentation.ui.contacts.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import com.vadym.myprofile.R
import com.vadym.myprofile.app.base.BaseFragment
import com.vadym.myprofile.app.utils.ext.safeNavigation
import com.vadym.myprofile.databinding.FragmentContactsBinding
import com.vadym.myprofile.presentation.model.ContactModel
import com.vadym.myprofile.presentation.ui.contacts.list.adapter.ContactAdapter
import com.vadym.myprofile.presentation.ui.contacts.list.adapter.ContactItemDecoration
import com.vadym.myprofile.presentation.ui.contacts.list.adapter.multiselect.ContactDetailsLookup
import com.vadym.myprofile.presentation.ui.contacts.list.adapter.multiselect.ContactKeyProvider
import com.vadym.myprofile.presentation.ui.main.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContactsFragment : BaseFragment<FragmentContactsBinding>(FragmentContactsBinding::inflate),
    ContactAdapter.IContactClickListener {

    private val contactAdapter: ContactAdapter by lazy { ContactAdapter(contactClickListener = this) }
    private val viewModel: ContactViewModel by viewModels()
    private lateinit var selectionTracker: SelectionTracker<ContactModel>
    private var actionMode: ActionMode? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initContactRecyclerView()
        initSelectionTracker()
        selectionTracker.onRestoreInstanceState(savedInstanceState)
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        requireActivity().invalidateMenu()
    }

    override fun setObservers() {
        viewModel.apply {
            contactsLiveData.observe(viewLifecycleOwner) {
                contactAdapter.submitList(it.filter { it.name.contains("s") })
            }
            isLoading.observe(viewLifecycleOwner) {
                if (it) {
                    binding.lpiLoading.show()
                } else {
                    binding.lpiLoading.hide()
                }
            }
            lifecycleScope.launch {
                eventsFlow.flowWithLifecycle(
                    viewLifecycleOwner.lifecycle,
                    Lifecycle.State.RESUMED
                ).collect {
                    showSnackbar(it)
                }
            }
        }
    }

    override fun removeUser(contact: ContactModel) {
        viewModel.removeContact(contact)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::selectionTracker.isInitialized) {
            selectionTracker.onSaveInstanceState(outState)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onContactLongClick(): Boolean {
        actionMode = requireActivity().startActionMode(
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
                    contactAdapter.notifyDataSetChanged()
                }
            }
        )
        return true
    }

    private fun setListeners() {
        binding.apply {
            tvAddContact.setOnClickListener {
                findNavController().safeNavigation(
                    MainFragmentDirections.actionMainFragmentToContactAddDialogFragment()
                )
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

    private fun initContactRecyclerView() {
        parentFragment?.postponeEnterTransition()
        binding.rvContactList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = contactAdapter
            addItemDecoration(
                ContactItemDecoration(
                    resources.getDimensionPixelSize(
                        R.dimen.item_contact_margin
                    )
                )
            )
        }
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
        addSelectionTrackerObserver()
    }

    private fun addSelectionTrackerObserver() {
        selectionTracker.addObserver(object : SelectionTracker.SelectionObserver<ContactModel>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()
                if (!selectionTracker.hasSelection()) {
                    actionMode?.finish()
                    binding.apply {
                        fabDeleteContacts.hide()
                        tvAddContact.isEnabled = true
                    }
                } else {
                    binding.tvAddContact.isEnabled = false
                    setSelectedTitle(selectionTracker.selection.size())
                }
            }
        })
    }

    private fun setSelectedTitle(selected: Int) {
        actionMode?.title = getString(R.string.contact_selection, selected)
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
}