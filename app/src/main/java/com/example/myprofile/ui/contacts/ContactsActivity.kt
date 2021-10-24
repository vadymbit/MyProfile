package com.example.myprofile.ui.contacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import com.example.myprofile.R
import com.example.myprofile.model.ContactModel
import com.example.myprofile.databinding.ActivityContactsBinding
import com.example.myprofile.ui.contacts.adapter.ContactAdapter
import com.example.myprofile.ui.contacts.adapter.ContactItemDecoration
import com.example.myprofile.ui.contacts.adapter.IContactClickListener

class ContactsActivity : AppCompatActivity(), IContactClickListener {

    private lateinit var contactAdapter: ContactAdapter
    private lateinit var binding: ActivityContactsBinding
    private val viewModel: ContactViewModel by viewModels()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.contact_toolbar_menu, menu)
        //val searchItem = menu?.findItem(R.id.action_search)
        //val searchView = searchItem?.actionView as SearchView
        return super.onCreateOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Set up toolbar and add back button
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        contactAdapter = ContactAdapter(contactClickListener = this)
        initContactRecyclerView()

        binding.textViewAddContact.setOnClickListener {
            ContactAddDialogFragment().show(supportFragmentManager, "Dialog")
        }
        viewModel.contacts.observe(this, {
            contactAdapter.submitList(it.toMutableList())
        })
    }

    /**
     * Set recycler view adapter and item decoration
     */
    private fun initContactRecyclerView() {
        binding.apply {
            contactList.adapter = contactAdapter
            contactList.addItemDecoration(
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
            ContactModel(
                username,
                career,
                phone,
                email,
                address,
                birthDate,
                uriPhoto
            )
        )
    }
}