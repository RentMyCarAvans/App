@file:Suppress("DEPRECATION")

package com.avans.rentmycar

import android.util.Log
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.avans.rentmycar.ui.home.HomeFragment
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BottomNavTest {

    @Test
    fun testDummy(){
        Log.d("[RMCUT]","testDummy() Start")
        assertEquals("PASSED","PASSED")
    }

    @Test
    fun testNav() {
        Log.d("[RMCUT]","testNav() Start")
        //Getting the NavController for test
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        Log.d("[RMCUT]","testNav() Step 1")

        //Launches the Fragment in isolation
        launchFragmentInContainer<HomeFragment>().onFragment { fragment ->
            //Setting the navigation graph for the NavController
            navController.setGraph(com.avans.rentmycar.R.navigation.nav_graph)
            Log.d("[RMCUT]","testNav() Step 2")

            //Sets the NavigationController for the specified View
            Navigation.setViewNavController(fragment.requireView(), navController)
            Log.d("[RMCUT]","testNav() Step 3")
        }

        // Verify that performing a click changes the NavController’s state
        onView(ViewMatchers.withId(R.id.mycars))
            .perform(ViewActions.click())
        Log.d("[RMCUT]","testNav() Step 4")

        assertEquals(
            navController.currentDestination?.id,
            R.id.mycars
        )
        Log.d("[RMCUT]","testNav() Step 5")
    }
}