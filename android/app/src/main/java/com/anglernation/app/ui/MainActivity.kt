package com.anglernation.app.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavHostFragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.anglernation.app.ui.auth.AuthRepository
import com.anglernation.app.ui.fragments.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var api: AnglerNationApi
    private lateinit var authRepository: AuthRepository
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize API
        api = AnglerNationApi.create()

        // Initialize auth repository
        authRepository = AuthRepository(requireApplication())

        // Check authentication - redirect to login if not authenticated
        if (!authRepository.isAuthenticated()) {
            // Navigate to login fragment
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController
            navController.navigate(R.id.nav_login)
        }

        // Setup navigation
        setupNavigation()

        // Setup bottom navigation
        setupBottomNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Setup action bar with nav controller
        setupActionBarWithNavController(navController, binding.toolbar)

        // Setup bottom navigation with nav controller
        setupWithNavController(binding.bottomNavigation, navController)
    }

    private fun setupBottomNavigation() {
        // Bottom navigation is handled by the Navigation Component via setupWithNavController
        // The bottom nav menu items correspond to the navigation graph destinations
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}