package com.anglernation.app.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DefaultItemAnimator
import com.squareup.moshi.Moshi
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.retrofit2.Retrofit
import com.squareup.retrofit2.converter.moshi.MoshiConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var api: AnglerNationApi
    private lateinit var rodAdapter: RodAdapter
    private var rodJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Use ViewBinding instead of findViewById
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup toolbar
        binding.toolbar.setTitle("AnglerNation")
        setSupportActionBar(binding.toolbar)

        // Initialize API and load data
        api = createApi()
        loadRods()

        // Setup bottom navigation
        setupBottomNavigation()

        // Setup RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.adapter = RodAdapter(emptyList()) { rod ->
            runOnUiThread {
                Toast.makeText(this, "Rod: ${rod.name}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_rods -> {
                    binding.recyclerView.visibility = android.view.View.VISIBLE
                    binding.toolbar.title = "Rods"
                    loadRods()
                    true
                }
                R.id.navigation_lures -> {
                    binding.recyclerView.visibility = android.view.View.VISIBLE
                    binding.toolbar.title = "Lures"
                    true
                }
                R.id.navigation_lines -> {
                    binding.recyclerView.visibility = android.view.View.VISIBLE
                    binding.toolbar.title = "Lines"
                    true
                }
                R.id.navigation_locations -> {
                    binding.recyclerView.visibility = android.view.View.VISIBLE
                    binding.toolbar.title = "Locations"
                    true
                }
                R.id.navigation_fish -> {
                    binding.recyclerView.visibility = android.view.View.VISIBLE
                    binding.toolbar.title = "Fish"
                    true
                }
                R.id.navigation_catches -> {
                    binding.recyclerView.visibility = android.view.View.VISIBLE
                    binding.toolbar.title = "Catches"
                    true
                }
                R.id.navigation_posts -> {
                    binding.recyclerView.visibility = android.view.View.VISIBLE
                    binding.toolbar.title = "Posts"
                    true
                }
                R.id.navigation_comments -> {
                    binding.recyclerView.visibility = android.view.View.VISIBLE
                    binding.toolbar.title = "Comments"
                    true
                }
                else -> false
            }
        }
    }

    private fun loadRods() {
        if (rodJob != null) rodJob.cancel()

        rodJob = lifecycleScope.launch(Dispatchers.IO) {
            try {
                val rods = api.getRods()
                lifecycleScope.launch(Dispatchers.Main) {
                    val adapter = binding.recyclerView.adapter as RodAdapter
                    adapter.copy(rodList = rods) { rod ->
                        runOnUiThread {
                            Toast.makeText(this, "Rod: ${rod.name}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    binding.recyclerView.adapter = adapter
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
                Log.e("MainActivity", "Failed to load rods", e)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        rodJob?.cancel()
        rodJob = null
        super.onDestroy()
    }
}