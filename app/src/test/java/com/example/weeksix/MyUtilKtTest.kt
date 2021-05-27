package com.example.weeksix

import org.junit.Test

import org.junit.Assert.*

class MyUtilKtTest {

    val name = "Thomas Shelby"
    val number = "09088543214"
    val mail = "tommy@gmail.com"

    @Test
    fun test_emailCheck() {
        assertFalse(emailCheck(mail))
    }

    @Test
    fun test_nameCheck() {
        assertFalse(nameCheck(name))
    }

    @Test
    fun test_numberCheck() {
        assertFalse(numberCheck(number))
    }
}