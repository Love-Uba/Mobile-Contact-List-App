package com.example.weeksix.firebasecontact

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.weeksix.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase


class FullContactFragment : Fragment() {

    /**
     *The full contact page of each contact item fetched from the database
     * */

    private val dataBase =
        FirebaseDatabase.getInstance("https://week-6-b5f6f-default-rtdb.firebaseio.com")
            .getReference("contacts")

    lateinit var contactName: TextView
    lateinit var contactNumber: TextView
    lateinit var contactMail: TextView
    lateinit var shareContact: ImageView
    lateinit var makeCall: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_full_contact, container, false)

        /**
         *Each view created in the xml file
         * */

        val editPage = WriteContact()

        contactName = rootView.findViewById(R.id.full_name)

        contactMail = rootView.findViewById(R.id.full_mail)

        contactNumber = rootView.findViewById(R.id.full_number)

        /**
         *The toolbar back button navigating back to the fragment the activity contains
         * */

        val tb = rootView.findViewById<androidx.appcompat.widget.Toolbar>(R.id.detail_toolbar)
        tb.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        /**
         *clickable images on the toolbar and fab to perform different actions of
         * editing, deleting, sharing and calling contact
         * */

        val editContact = rootView.findViewById<ImageView>(R.id.edit_button)

        val deleteContacts = rootView.findViewById<ImageView>(R.id.delete_button)

        shareContact = rootView.findViewById(R.id.share_button)

        makeCall = rootView.findViewById<FloatingActionButton>(R.id.my_call_fab)

        /**
         *Each contact full detail passed from the list page to secure the id from the database
         * */

        val contactId = arguments?.getString("id")!!
        contactMail.text = arguments?.getString("email")
        val contactColor = arguments?.getInt("color")!!
        contactName.text = arguments?.getString("namie")
        contactNumber.text = arguments?.getString("numbie")

        /**
         *Edit button activated to pass the details fetched to populate its edit text and open the fragment
         * */


        editContact.setOnClickListener {

            var bundle = Bundle()
            bundle.putString("title", "Edit Contact")
            bundle.putString("id", contactId)
            bundle.putString("oldName", contactName.text.toString())
            bundle.putString("oldNumber", contactNumber.text.toString())
            bundle.putString("oldEmail", contactMail.text.toString())
            bundle.putInt("oldColor", contactColor)

            editPage.arguments = bundle

            val transaction = activity?.supportFragmentManager?.beginTransaction()

            transaction?.replace(R.id.fragment_container, editPage)

            transaction?.addToBackStack(null)?.commit()
        }

        /**
         *Delete button to remove corresponding id selected and passed from the list inflating the fragment
         * */

        deleteContacts.setOnClickListener {
            dataBase.child(contactId).removeValue()

            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, ListFragment())
            transaction?.addToBackStack(null)?.commit()
        }

        /**
         *Call button activated to first request permission to place a call from the phone using the
         * @link makeAcall() function
         * */

        makeCall.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), 110)
            } else makeAcall()
        }

        /**
         *Share button activated to share contact details to all applications available to process the intent using
         * @link shareNumber() function
         * */

        shareContact.setOnClickListener {
            shareNumber()
        }

        return rootView
    }

    fun makeAcall() {
        var callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel: ${contactNumber.text}")
        startActivity(callIntent)
    }

    fun shareNumber() {
        var sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.putExtra(Intent.EXTRA_TEXT, "${contactName.text}")
        sendIntent.putExtra(Intent.EXTRA_TEXT, "${contactNumber.text}")
        sendIntent.putExtra(Intent.EXTRA_TEXT, "${contactMail.text}")
        sendIntent.type = "text/plain"

        startActivity(Intent.createChooser(sendIntent, "Share this contact using:"))
    }

    /**
     *To check the user's reaction to the permission requested and match the response
     * */

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 110 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            makeAcall()
        } else {
            Toast.makeText(
                activity,
                "Accept permission, to make calls from this app",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}