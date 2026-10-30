package com.anglernation.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anglernation.app.ui.models.LureDto
import com.anglernation.app.ui.adapters.LureAdapter
import com.anglernation.app.ui.ui.LureFragmentBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

class LureFragment : Fragment() {

    private var _binding: LureFragmentBinding? = null
    private val binding get() = _binding!!

    private val api = AnglerNationApi.create()
    private val lureList = MutableStateFlow<List<LureDto>>(emptyList())
    private var listJob: kotlinx.coroutines.Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LureFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = LureAdapter(lureList.asFlow().collectAsList()) { lure ->
            // Handle lure click
        }

        // Load lures
        loadLures()
    }

    private fun loadLures() {
        if (listJob != null) listJob.cancel()
        listJob = lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = api.getLures(page = 0, size = 20)
                lureList.value = response.content
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