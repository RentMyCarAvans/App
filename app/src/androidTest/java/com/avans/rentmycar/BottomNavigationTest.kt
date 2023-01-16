package com.avans.rentmycar

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BottomNavigationTest {
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testBottomNavigationToHome() {

        // Click on the tab "Rent My Car", and check if selected
        Thread.sleep(1000)
        onView(withId(com.avans.rentmycar.R.id.home)).perform(click()).check(matches(isSelected()))
        Thread.sleep(2000)
    }

    @Test
    fun testBottomNavigationToMyOffers() {

        // Click on the tab "My offers", and check if selected
        Thread.sleep(1000)
        onView(withId(com.avans.rentmycar.R.id.myOffers)).perform(click()).check(matches(isSelected()))
        Thread.sleep(2000)
    }

    @Test
    fun testBottomNavigationToOfferMyCar() {

        // Click on the tab "Offer My Car", and check if selected
        Thread.sleep(1000)
        onView(withId(R.id.myCars)).perform(click()).check(matches(isSelected()))
        Thread.sleep(2000)
    }

    @Test
    fun testBottomNavigationToProfile() {

        // Click on the tab "Profile", and check if selected
        Thread.sleep(2000)
        onView(withId(R.id.profile)).perform(click()).check(matches(isSelected()))
        Thread.sleep(2000)
    }

}