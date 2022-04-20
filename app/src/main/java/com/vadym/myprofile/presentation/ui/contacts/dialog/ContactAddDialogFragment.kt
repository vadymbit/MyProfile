package com.vadym.myprofile.presentation.ui.contacts.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.vadym.myprofile.R
import com.vadym.myprofile.app.base.BaseDialogFragment
import com.vadym.myprofile.databinding.FragmentContactAddDialogBinding
import com.vadym.myprofile.presentation.model.ContactModel
import com.vadym.myprofile.presentation.ui.contacts.dialog.adapter.ContactAddAdapter
import com.vadym.myprofile.presentation.ui.contacts.list.adapter.ContactItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContactAddDialogFragment :
    BaseDialogFragment<FragmentContactAddDialogBinding>(FragmentContactAddDialogBinding::inflate),
    ContactAddAdapter.IContactAddClickListener {

    private val viewModel: ContactAddViewModel by viewModels()
    private val addContactAdapter by lazy { ContactAddAdapter(contactAddClickListener = this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        setToolbar()
        initRecyclerView()
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun addContact(contact: ContactModel) {
        viewModel.addContact(contact)
    }

    override fun setObservers() {
        viewModel.apply {
            usersLiveData.observe(viewLifecycleOwner) {
                addContactAdapter.submitList(it)
            }
            lifecycleScope.launch {
                eventsFlow.flowWithLifecycle(
                    viewLifecycleOwner.lifecycle,
                    Lifecycle.State.RESUMED
                ).collect {
                    Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                }
            }
            isLoading.observe(viewLifecycleOwner) {
                if (it) {
                    binding.lpiLoading.show()
                } else {
                    binding.lpiLoading.hide()
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvUserList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = addContactAdapter
            addItemDecoration(
                ContactItemDecoration(
                    resources.getDimensionPixelSize(
                        R.dimen.item_contact_margin
                    )
                )
            )
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
}
