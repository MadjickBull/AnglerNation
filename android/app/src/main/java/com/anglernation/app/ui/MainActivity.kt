package com.anglernation.app.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavDeepLink
import androidx.navigation.Navigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.anglernation.app.ui.fragments.*
import com.anglernation.app.ui.adapters.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var api: AnglerNationApi
    private lateinit var authRepository: AuthRepository
    private lateinit var navController: NavController

    // Fragment references for bottom navigation
    private var currentFragment: String = "rods"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize API
        api = createApi()

        // Initialize auth repository
        authRepository = AuthRepository(requireApplication())

        // Setup navigation
        setupNavigation()

        // Setup bottom navigation
        setupBottomNavigation()

        // Load initial data
        loadCurrentFragment()
    }

    private fun createApi(): AnglerNationApi {
        return AnglerNationApi.create()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Setup action bar with nav controller
        setupActionBarWithNavController(navController, binding.toolbar)

        // Setup bottom navigation with nav controller
        setupWithNavController(binding.bottomNavigation, navController)

        // Set up bottom nav item selection listener as fallback
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_rods -> {
                    navController.navigate(R.id.action_nav_rods_to_nav_rods)
                    binding.toolbar.title = "Rods"
                    currentFragment = "rods"
                    true
                }
                R.id.navigation_lures -> {
                    navController.navigate(R.id.action_nav_rods_to_nav_lures)
                    binding.toolbar.title = "Lures"
                    currentFragment = "lures"
                    true
                }
                R.id.navigation_lines -> {
                    navController.navigate(R.id.action_nav_rods_to_nav_lines)
                    binding.toolbar.title = "Lines"
                    currentFragment = "lines"
                    true
                }
                R.id.navigation_locations -> {
                    navController.navigate(R.id.action_nav_rods_to_nav_locations)
                    binding.toolbar.title = "Locations"
                    currentFragment = "locations"
                    true
                }
                R.id.navigation_fish -> {
                    navController.navigate(R.id.action_nav_rods_to_nav_fish)
                    binding.toolbar.title = "Fish"
                    currentFragment = "fish"
                    true
                }
                R.id.navigation_catches -> {
                    navController.navigate(R.id.action_nav_rods_to_nav_catches)
                    binding.toolbar.title = "Catches"
                    currentFragment = "catches"
                    true
                }
                R.id.navigation_posts -> {
                    navController.navigate(R.id.action_nav_rods_to_nav_posts)
                    binding.toolbar.title = "Posts"
                    currentFragment = "posts"
                    true
                }
                R.id.navigation_comments -> {
                    navController.navigate(R.id.action_nav_rods_to_nav_comments)
                    binding.toolbar.title = "Comments"
                    currentFragment = "comments"
                    true
                }
                else -> false
            }
        }
    }

    private fun loadCurrentFragment() {
        // Load data for the current fragment based on saved state
        when (currentFragment) {
            "rods" -> loadRods()
            "lures" -> loadLures()
            "lines" -> loadLines()
            "locations" -> loadLocations()
            "fish" -> loadFish()
            "catches" -> loadCatches()
            "posts" -> loadPosts()
            "comments" -> loadComments()
        }
    }

    private fun loadRods() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val rods = api.getRods(page = 0, size = 20)
                lifecycleScope.launch(Dispatchers.Main) {
                    // Update the current fragment's adapter
                    when (currentFragment) {
                        "rods" -> {
                            // TODO: update rod adapter
                        }
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
                Log.e("MainActivity", "Failed to load rods", e)
            }
        }
    }

    private fun loadLures() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val lures = api.getLures(page = 0, size = 20)
                lifecycleScope.launch(Dispatchers.Main) {
                    when (currentFragment) {
                        "lures" -> {
                            // TODO: update lure adapter
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadLines() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val lines = api.getLines(page = 0, size = 20)
                lifecycleScope.launch(Dispatchers.Main) {
                    when (currentFragment) {
                        "lines" -> {
                            // TODO: update line adapter
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadLocations() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val locations = api.getLocations(page = 0, size = 20)
                lifecycleScope.launch(Dispatchers.Main) {
                    when (currentFragment) {
                        "locations" -> {
                            // TODO: update location adapter
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadFish() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val fish = api.getFish(page = 0, size = 20)
                lifecycleScope.launch(Dispatchers.Main) {
                    when (currentFragment) {
                        "fish" -> {
                            // TODO: update fish adapter
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadCatches() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val catches = api.getCatches(page = 0, size = 20)
                lifecycleScope.launch(Dispatchers.Main) {
                    when (currentFragment) {
                        "catches" -> {
                            // TODO: update catch adapter
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadPosts() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val posts = api.getPosts(page = 0, size = 20)
                lifecycleScope.launch(Dispatchers.Main) {
                    when (currentFragment) {
                        "posts" -> {
                            // TODO: update post adapter
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadComments() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val comments = api.getComments(page = 0, size = 20)
                lifecycleScope.launch(Dispatchers.Main) {
                    when (currentFragment) {
                        "comments" -> {
                            // TODO: update comment adapter
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }