package com.avans.rentmycar

import android.util.Log
import android.view.View
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.avans.rentmycar.databinding.AddCarItemBinding
import com.avans.rentmycar.ui.mycars.CarAddItemFragment
import com.avans.rentmycar.ui.mycars.MyCarsFragment
import com.avans.rentmycar.ui.profile.ProfileFragment
import org.hamcrest.Matchers.containsString
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CarAddItemTests {

    // In order to interact with the activity, the testcase must first launch it
    @get:Rule()
    val activity = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var scenario: FragmentScenario<CarAddItemFragment>

    @Before
    fun setUp(){
        Log.d("[RMCUT]","setUp() Start")
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_RentMyCar)
        // launchFragment<ProfileFragment>(themeResId = R.style.Theme_RentMyCar)
        Log.d("[RMCUT]","setUp() Step 1")
         scenario.onFragment {

         }
            //it.getActivity()?.getResources()?.getResourceName(it.getActivity()?.getTheme().getResourceId());
        //}
        scenario.moveToState(Lifecycle.State.STARTED)
        Log.d("[RMCUT]","setUp() Step 2")

    }

    @Test
    fun testCarAddItemFragment() {
        Log.d("[RMCUT]","testCarAddItemFragment() Start")
        onView(withId(R.id.txtInput_carLicensePlate))
            .perform(typeText("HF067X"))
            .perform(ViewActions.closeSoftKeyboard())
        Log.d("[RMCUT]","testCarAddItemFragment() step1")

    }

    @Test
    fun testValidLicensePlate(){
        Log.d("[RMCUT]","testValidLicensePlate() Start")

        // Enter valid licenseplate HF067X
        onView(withId(R.id.txtInput_carLicensePlate))
            .perform(typeText("HF067X"))
            .perform(ViewActions.closeSoftKeyboard())

        // Click on the GET button to retrieve cardetails
        onView(withId(R.id.button_car_get_rdwdetails))
            .perform(click())

        // Check to see if actual outcome matches expected outcome
        onView(withId(R.id.txtInput_carVehicle))
            .check(matches(withText(containsString("KIA CEE D"))))
    }




}