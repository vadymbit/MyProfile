package com.vadym.myprofile.presentation.ui.contacts.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
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
    ContactAdapter.IContactClickListener, ActionMode.Callback, MenuProvider {

    private val contactAdapter: ContactAdapter by lazy { ContactAdapter(contactClickListener = this) }
    private val viewModel: ContactViewModel by viewModels()
    private lateinit var selectionTracker: SelectionTracker<ContactModel>
    private var actionMode: ActionMode? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menuInit()
        initContactRecyclerView()
        initSelectionTracker()
        selectionTracker.onRestoreInstanceState(savedInstanceState)
        setListeners()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::selectionTracker.isInitialized) {
            selectionTracker.onSaveInstanceState(outState)
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.contact_toolbar_menu, menu)
        val searchButton = menu.findItem(R.id.action_search).actionView as SearchView
        searchButton.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.searchContact(it)
                }
                return true
            }
        })
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return menuItem.itemId == R.id.action_search
    }

    override fun setObservers() {
        viewModel.apply {
            contactsLiveData.observe(viewLifecycleOwner) {
                contactAdapter.submitList(it)
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

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        binding.fabDeleteContacts.show()
        contactAdapter.notifyDataSetChanged()
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = true

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean = true

    @SuppressLint("NotifyDataSetChanged")
    override fun onDestroyActionMode(mode: ActionMode?) {
        selectionTracker.clearSelection()
        contactAdapter.notifyDataSetChanged()
    }

    override fun onContactLongClick(): Boolean {
        actionMode = requireActivity().startActionMode(this)
        return true
    }

    private fun menuInit() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
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
