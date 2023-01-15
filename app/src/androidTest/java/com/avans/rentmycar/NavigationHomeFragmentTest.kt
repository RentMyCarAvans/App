package com.avans.rentmycar

import android.util.Log
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.avans.rentmycar.ui.mycars.MyCarsFragment

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class NavigationHomeFragmentTest {

    @Test
    fun testHomeFragmentNavigation(){

        // Setup
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        // Nav to My bookings
        onView(withId(R.id.button_home_mybookings))  // should correspond with id in fragmentxml
            .perform(click())

        // Verify that the My bookings is displayed
        onView(withId(R.id.button_home_mybookings))
            .check(matches(isDisplayed()))

        // Nav to Available cars
        //onView(withId(R.id.button_home_availablecars))  // should correspond with id in fragmentxml
        //    .perform(NavigationViewActions.navigateTo(R.id.mycars))


        // Verify that the Available cars is displayed
        //onView(withId(R.id.button_home_availablecars))
        //    .check(matches(isDisplayed()))

    }

    @Test
    fun testHomeProfileFragmentNavigation(){

        // Setup
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

       // onView(withId(R.id.profileFragment))
       //     .perform(click())
    }

    @Test
    fun testMyCarsFragmentNavigation(){

        // Setup
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        // Nav to Available cars
        onView(withText("Offer My Car"))  // should correspond with id in fragmentxml
            .perform(NavigationViewActions.navigateTo(R.id.mycars))

    }

    @Test
    fun testMyOffersFragmentNavigation(){

        // Setup
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)


    }

    @Test
    fun testMyCarsFragmentXXX(){
        Log.d("RMCUT","testMyCarsFragmentXXX")
        val scenario = launchFragmentInContainer<MyCarsFragment>(
            initialState = Lifecycle.State.INITIALIZED)
    }
}