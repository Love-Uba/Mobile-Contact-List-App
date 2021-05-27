package com.example.weeksix.phonecontact

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weeksix.ItemEntity
import com.example.weeksix.R
import com.example.weeksix.firebasecontact.ListFragment

class PersonalContactFragment : Fragment() {

    var phoneList = arrayListOf<ItemEntity>()
    lateinit var myRecyclerView: RecyclerView
    var myPhoneAdapter = PhoneAdapter(phoneList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_personal_contact, container, false)

        var mySwitch = rootView.findViewById<ImageView>(R.id.switchie)

        /**
         *An image switch for switching between implementations of
         * already existing phone contacts and new database contacts.
         * */

        mySwitch.setOnClickListener {
            var transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, ListFragment())
            transaction?.addToBackStack(null)?.commit()
        }

        /**
         *Recyclerview adapter is populated with phone contact data list once permission
         * to read the data declared in the manifest is granted
         * */

        myRecyclerView = rootView.findViewById<RecyclerView>(R.id.my_recycle)

        myRecyclerView.layoutManager = LinearLayoutManager(activity)

        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), 111)
        } else getContacts()


        return rootView
    }

    /**
     *Accessing phone contact to populate the data class containing the required properties
     * all duly assigned to their matching recycler view item and the fetched values are added to a list
     * used to populated the recycler view
     * */

    fun getContacts() {
        val contacts = activity?.contentResolver
            ?.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")
        while (contacts!!.moveToNext()) {
            val name =
                contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number =
                contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val itemEntity = ItemEntity("0", name, number)
            phoneList.add(itemEntity)
        }

        myRecyclerView.adapter = PhoneAdapter(phoneList)
        contacts.close()
    }

    /**
     *Check user reaction to the permission requested and match the response
     * */

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getContacts()
        } else {
            Toast.makeText(
                activity,
                "Accept permission, to read your contacts on this app",
                Toast.LENGTH_LONG
            ).show()
        }
    }

}