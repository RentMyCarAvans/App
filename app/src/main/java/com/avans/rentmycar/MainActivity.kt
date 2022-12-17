package com.avans.rentmycar

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.avans.rentmycar.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val bottomNavigationView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)



        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )


        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)

        if(userIsLoggedIn()) {
            navController.navigate(R.id.action_introFragment_to_homeFragment)

        }
        // hide navbar on login page
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.parent?.id == R.id.introFragment  || destination.parent?.id == R.id.loginFragment ) {
                if (!userIsLoggedIn())
                bottomNavigationView.visibility = View.GONE
                else {
                    bottomNavigationView.visibility = View.VISIBLE

                }
            }
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
    private fun userIsLoggedIn(): Boolean {
        return true
    }
}