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
import kotlinx.coroutines.launch
import android.widget.Toast

// API data models compatible with Moshi
data class Rod(
    val id: String,
    val name: String,
    val description: String? = null,
    val createdAt: String? = null
import com.squareup.retrofit2.Retrofit
import com.squareup.retrofit2.converter.scalars.ScalarsConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator

// API data models
data class Rod(
    val id: String,
    val name: String,
    val description: String?,
    val createdAt: String?
)

data class RodDto(
    val id: String,
    val name: String,
    val description: String? = null,
    val createdAt: String? = null
    val description: String?,
    val createdAt: String?
)

// API interface
interface AnglerNationApi {
    @GET("rods")
    suspend fun getRods(): List<RodDto>

    companion object {
        const val BASE_URL = "http://10.0.2.2:8081/"
    }
}

// Moshi instance with Kotlin adapter support
val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Retrofit setup with Moshi converter
fun createApi(): AnglerNationApi {
    return Retrofit.Builder()
        .baseUrl(AnglerNationApi.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        const val BASE_URL = "http://10.0.2.2:8081/"  // LAN IP for emulator, replace with ngrok URL
    }
}

// Retrofit setup
fun createApi(): AnglerNationApi {
    return Retrofit.Builder()
        .baseUrl(AnglerNationApi.BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
        .create(AnglerNationApi::class.java)
}

// Rod adapter for RecyclerView
class RodAdapter(
    private val rodList: List<Rod>,
    onRodClick: (Rod) -> Unit
) : Adapter<RodAdapter.RodViewHolder>() {

    inner class RodViewHolder(view: View) : ViewHolder(view) {
        val nameText: android.widget.TextView = view.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RodViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
) : androidx.recyclerview.widget.RecyclerView.Adapter<RodAdapter.RodViewHolder>() {

    inner class RodViewHolder(view: androidx.viewwidget.View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val nameText = view.findViewById(android.R.id.text1) as android.widget.TextView
    }

    override fun onCreateViewHolder(parent: androidx.viewwidget.ViewGroup, viewType: Int): RodViewHolder {
        val view = androidx.viewwidget.LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return RodViewHolder(view)
    }

    override fun onBindViewHolder(holder: RodViewHolder, position: Int) {
        val rod = rodList[position]
        holder.nameText.text = rod.name
        holder.itemView.setOnClickListener {
            onRodClick(rod)
        }
    }

    override fun getItemCount(): Int = rodList.size
}

// Main Activity with ViewBinding and Moshi
class MainActivity : AppCompatActivity() {

    private lateinit var binding: activity_main_binding
    private lateinit var api: AnglerNationApi
// Main Activity
class MainActivity : AppCompatActivity() {

    private lateinit var api: AnglerNationApi
    private lateinit var rodAdapter: RodAdapter
    private var rodJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use ViewBinding instead of findViewById
        binding = activity_main_binding.inflate(layoutInflater)
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
        setContentView(R.layout.activity_main)

        // Setup toolbar/navigation
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(androidx.appcompat.R.id.toolbar)
        setSupportActionBar(toolbar)

        // Setup navigation tabs
        setupNavigation()

        // Setup RecyclerView
        rodAdapter = RodAdapter(emptyList()) { rod ->
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


        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(androidx.recyclerview.R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = rodAdapter

        // Initialize API and load data
        api = createApi()
        loadRods()
    }

    private fun setupNavigation() {
        // Bottom navigation or tabs setup
        val bottomNav = bottomNavigationView!! // would need to add to layout
    }

    private fun loadRods() {
        rodJob?.cancel()
        rodJob = lifecycleScope.launch(Dispatchers.IO) {
            try {
                val rods = api.getRods()
                lifecycleScope.launch(Dispatchers.Main) {
                    val adapter = binding.recyclerView.adapter as RodAdapter
                    adapter = RodAdapter(rods) { rod ->
                    rodAdapter = RodAdapter(rods) { rod ->
                        runOnUiThread {
                            Toast.makeText(this, "Rod: ${rod.name}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    binding.recyclerView.adapter = adapter
                    recyclerView.adapter = rodAdapter
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
    }
}
