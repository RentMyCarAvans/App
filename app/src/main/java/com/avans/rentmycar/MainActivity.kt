package com.avans.rentmycar

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.avans.rentmycar.R
import com.avans.rentmycar.databinding.ActivityMainBinding
import com.avans.rentmycar.utils.SessionManager
import com.bumptech.glide.annotation.GlideModule
import com.google.android.material.bottomnavigation.BottomNavigationView

@GlideModule
class MainActivity : AppCompatActivity() {
    private val TAG = "[RMC][MainActivity]"
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)






        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_container
        ) as NavHostFragment
        navController = navHostFragment.navController

        // Setup the bottom navigation view with navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.setupWithNavController(navController)

        // Setup the ActionBar with navController and 3 top level destinations
        appBarConfiguration = AppBarConfiguration(
            setOf( R.id.homeFragment,  R.id.mycars, R.id.profileFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.loginFragment || destination.id == R.id.registerFragment || destination.id == R.id.introFragment || destination.id == R.id.introFragment2) {

                bottomNavigationView.visibility = View.GONE
            } else {

                bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }

        override fun onSupportNavigateUp(): Boolean {
            return navController.navigateUp(appBarConfiguration)
        }

}