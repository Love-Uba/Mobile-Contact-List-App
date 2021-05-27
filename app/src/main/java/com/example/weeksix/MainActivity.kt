package com.example.weeksix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weeksix.firebasecontact.ListFragment
import com.example.weeksix.phonecontact.PersonalContactFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         *Inflates the activity on launch of the app with the phone contact list fragment
         * */


        val myPhoneContact = PersonalContactFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, myPhoneContact).commit()

    }
}