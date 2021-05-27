package com.example.weeksix.firebasecontact

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weeksix.*
import com.example.weeksix.R
import com.example.weeksix.phonecontact.PersonalContactFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class ListFragment : Fragment(), ContactAdapter.MyItemClickListener {

    /**
     *List class holding the populated recyclerview containing the contact details fetched from the database
     * */


    val dataBase = FirebaseDatabase.getInstance("https://week-6-b5f6f-default-rtdb.firebaseio.com")
        .getReference("contacts")

    lateinit var nameClicked: String
    lateinit var numberClicked: String
    var bundle = Bundle()
    lateinit var myRecycler: RecyclerView
    lateinit var myContactAdapter: ContactAdapter
    lateinit var myName: String
    lateinit var myNumber: String
    lateinit var myEmail: String
    val userList = ArrayList<ItemEntity>()
    lateinit var totalContact: ItemEntity

    companion object {
        const val NAME_KEY = "namie"
        const val NUMBER_KEY = "numbie"
        const val ID_KEY = "id"
        const val EMAIL_KEY = "email"
        const val COLOR_KEY = "color"

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_list, container, false)

        val writeContact = WriteContact()

        val myAddContact = rootView.findViewById<FloatingActionButton>(R.id.addContactFab)

        /**
         *The add new contact FAB Inflates the add contact fragment
         * @see WriteContact() class
         * */

        myAddContact.setOnClickListener {
            val bundle = bundleOf("title" to "Create Contact")
            writeContact.arguments = bundle
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, writeContact)
            transaction?.addToBackStack(null)?.commit()

        }

        /**
         *Recyclerview adapter is populated by the value event listener saving data in form of snapshots
         * of the database content and the desired items are populated in the list
         * */

        myRecycler = rootView.findViewById(R.id.my_recycler)

        var getData = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                // var myBuilder = StringBuilder()
                if (snapshot != null) {
                    userList.clear()
                }
                for (i in snapshot.children) {

                    myName = i.child("/fullName").value.toString()
                    myNumber = i.child("/phone").value.toString()
                    myEmail = i.child("/email").value.toString()
                    totalContact = ItemEntity(i.key.toString(), myName, myNumber, myEmail)
                    userList.add(totalContact)

                    myContactAdapter =
                        ContactAdapter(
                            userList,
                            this@ListFragment
                        )
                    myRecycler.adapter = myContactAdapter

                }

            }
        }

        dataBase.addValueEventListener(getData)
        dataBase.addListenerForSingleValueEvent(getData)

        myRecycler.layoutManager = LinearLayoutManager(activity)

        /**
         *An image switch to switch between implementations of new database contacts and already existing phone contacts
         * */

        var mySwitch = rootView.findViewById<ImageView>(R.id.switchy)

        mySwitch.setOnClickListener {
            var transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, PersonalContactFragment())
            transaction?.addToBackStack(null)?.commit()
        }


        return rootView
    }

    /**
     *Inflates the full contact fragment on click of the fragment and pass all required values to populate the fragement
     * fetched from the database
     * */

    override fun onItemClick(position: Int) {

        var myFullContact =
            FullContactFragment()

        nameClicked = myContactAdapter.myDataEntity[position].fullName.toString()
        numberClicked = myContactAdapter.myDataEntity[position].phone.toString()

        bundle.putString(NAME_KEY, nameClicked)
        bundle.putString(NUMBER_KEY, numberClicked)
        bundle.putString(ID_KEY, myContactAdapter.myDataEntity[position].id)
        bundle.putString(EMAIL_KEY, myContactAdapter.myDataEntity[position].email)
        bundle.putInt(COLOR_KEY, myContactAdapter.myDataEntity[position].color)


        myFullContact.arguments = bundle

        var transaction = activity?.supportFragmentManager?.beginTransaction()

        transaction?.replace(R.id.fragment_container, myFullContact)

        transaction?.addToBackStack(null)?.commit()


    }

}