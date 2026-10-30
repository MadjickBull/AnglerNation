package com.anglernation.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anglernation.app.ui.models.FishDto
import com.anglernation.app.ui.adapters.FishAdapter
import com.anglernation.app.ui.ui.FishFragmentBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

class FishFragment : Fragment() {

    private var _binding: FishFragmentBinding? = null
    private val binding get() = _binding!!

    private val api = AnglerNationApi.create()
    private val fishList = MutableStateFlow<List<FishDto>>(emptyList())
    private var listJob: kotlinx.coroutines.Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FishFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = FishAdapter(fishList.asFlow().collectAsList()) { fish ->
            // Handle fish click
        }

        // Load fish
        loadFish()
    }

    private fun loadFish() {
        if (listJob != null) listJob.cancel()
        listJob = lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = api.getFish(page = 0, size = 20)
                fishList.value = response.content
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        listJob?.cancel()
        _binding = null
    }
}