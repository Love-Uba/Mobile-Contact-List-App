package com.example.weeksix

import com.google.firebase.database.FirebaseDatabase

/**
 *Utility class holding validation functions of provided data from the forms
 * */


fun emailCheck(mail: String): Boolean {
    if (mail.contains('@') && mail.isNotEmpty()) {
        return false
    }
    return true
}

fun nameCheck(name: String): Boolean {
    if (name.isNotEmpty()) {
        return false
    }
    return true
}

fun numberCheck(number: String): Boolean {
    if (number.isNotEmpty()) {
        return false
    }
    return true
}
