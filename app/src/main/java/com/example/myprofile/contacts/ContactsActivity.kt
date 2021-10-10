package com.example.myprofile.contacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import com.example.myprofile.R
import com.example.myprofile.data.Contact
import com.example.myprofile.databinding.ActivityContactsBinding

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

        contactAdapter = ContactAdapter(iContactClickListener = this)
        initContactRecyclerView()

        binding.textViewAddContact.setOnClickListener {
            ContactAddDialogFragment(viewModel).show(supportFragmentManager, "Dialog")
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

    override fun removeUser(contact: Contact) {
        viewModel.removeContact(contact)
    }
}