package com.example.weeksix

import org.junit.Assert.*

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

class MyMainActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_isActivityInView() {
        onView(withId(R.id.main)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isfragmentcontainer_inView() {
        onView(withId(R.id.fragment_container)).check(matches(isDisplayed()))
    }

    @Test
    fun test_implementation_Switch() {
        onView(withId(R.id.switchy)).perform(click())
        onView(withId(R.id.personal_id)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isAddContactbutton_Opening_Write_Fragment(){
        onView(withId(R.id.addContactFab)).perform(click())
        onView(withId(R.id.write_contact_page)).check(matches(isDisplayed()))
    }

}
