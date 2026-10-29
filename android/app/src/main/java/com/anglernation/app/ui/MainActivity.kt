package com.anglernation.app.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    val description: String?,
    val createdAt: String?
)

// API interface
interface AnglerNationApi {
    @GET("rods")
    suspend fun getRods(): List<RodDto>

    companion object {
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

// Main Activity
class MainActivity : AppCompatActivity() {

    private lateinit var api: AnglerNationApi
    private lateinit var rodAdapter: RodAdapter
    private var rodJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                    rodAdapter = RodAdapter(rods) { rod ->
                        runOnUiThread {
                            Toast.makeText(this, "Rod: ${rod.name}", Toast.LENGTH_SHORT).show()
                        }
                    }
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
    }
}
