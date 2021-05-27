package com.example.weeksix.firebasecontact

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.weeksix.*
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.database.FirebaseDatabase

class WriteContact : Fragment() {

    private val myDatabase =
        FirebaseDatabase.getInstance("https://week-6-b5f6f-default-rtdb.firebaseio.com")
    val myListFragment = ListFragment()
    var myRef = myDatabase.getReference("contacts")
    lateinit var id: String
    lateinit var email: String
    var color = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_write_contact, container, false)


        val myToolbar = rootView.findViewById<MaterialToolbar>(R.id.my_toolbar)
        val saveContact = rootView.findViewById<Button>(R.id.save_contact)
        var number = rootView.findViewById<EditText>(R.id.write_contact)
        val writeContact = number.text
        var name = rootView.findViewById<EditText>(R.id.write_name)
        val writeName = name.text
        var email = rootView.findViewById<EditText>(R.id.write_email)
        val writeEmail = email.text

        /**
         *The toolbar back button navigating back to the fragment the activity contains
         * */

        val tb = rootView.findViewById<androidx.appcompat.widget.Toolbar>(R.id.my_toolbar)
        tb.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        /**
         *A constant title is fetched from the full contact and the content
         * determines if the write page is for editing contacts or updating
         *
         * */

        myToolbar.title = arguments?.getString("title")

        if (myToolbar.title == "Edit Contact") {
            number.setText(arguments?.getString("oldNumber").toString())
            name.setText(arguments?.getString("oldName").toString())
            id = arguments?.getString("id").toString()
            email.setText(arguments?.getString("oldEmail").toString())
            color = arguments?.getInt("oldColor")!!
        }

        /**
         *The save button verifies the fields are filled correctly
         * */

        saveContact.setOnClickListener {
            when {
                emailCheck(email.text.toString()) -> Toast.makeText(
                    activity,
                    "Please enter a correct mail",
                    Toast.LENGTH_SHORT
                ).show()
                nameCheck(name.text.toString()) -> Toast.makeText(
                    activity,
                    "Please enter a name",
                    Toast.LENGTH_SHORT
                ).show()
                numberCheck(number.text.toString()) -> Toast.makeText(
                    activity,
                    "Please enter a number",
                    Toast.LENGTH_SHORT
                ).show()
                else -> {
                    if (myToolbar.title == "Edit Contact") {
                        updateContact(name, number, email)
                    } else {
                        addContact(name, number, email)
                    }
                }
            }
        }
        return rootView
    }

    /**
     *Adds new contact from the text field and opens the list containing all contacts
     * */

    fun addContact(writeName: EditText, writeContact: EditText, writeEmail: EditText) {
        var id = myRef.push().key!!
        val myItemEntity = ItemEntity(
            id,
            "${writeName.text}",
            "${writeContact.text}",
            "${writeEmail.text}"
        )

        myRef.child(id).setValue(myItemEntity)

        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment_container, myListFragment)
        transaction?.addToBackStack(null)?.commit()

    }

    /**
     *Updates existing contacts
     * */

    fun updateContact(writeName: EditText, writeContact: EditText, writeEmail: EditText) {
        val myItemEntity = ItemEntity(
            id,
            "${writeName.text}",
            "${writeContact.text}",
            "${writeEmail.text}", color
        )

        myRef.child(id).setValue(myItemEntity)

        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment_container, myListFragment)
        transaction?.addToBackStack(null)?.commit()
    }

}