package com.anglernation.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anglernation.app.ui.models.LineDto
import com.anglernation.app.ui.adapters.LineAdapter
import com.anglernation.app.ui.ui.LineFragmentBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.ui.showSnackbar

class LineFragment : Fragment() {

    private var _binding: LineFragmentBinding? = null
    private val binding get() = _binding!!

    private val api = AnglerNationApi.create()
    private val lineList = MutableStateFlow<List<LineDto>>(emptyList())
    private var listJob: kotlinx.coroutines.Job? = null
    private var adapter: LineAdapter? = null

    // Loading state
    private sealed class ListState {
        data class Loading : ListState()
        data class Success(val items: List<LineDto>) : ListState()
        data class Error(val exception: Exception) : ListState()
    }

    private var listState = ListState.Loading

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LineFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = LineAdapter(lineList.asFlow().collectAsList()) { line ->
            // Handle line click
        }
        binding.recyclerView.adapter = adapter

        // Setup state observer
        observeListState()

        // Load lines
        loadLines()
    }

    private fun observeListState() {
        lifecycleScope.launch(LifecycleScope.viewLifecycleOwner(this)) {
            while (true) {
                when (listState) {
                    is ListState.Loading -> {
                        binding.progressBar.visibility = android.view.View.VISIBLE
                        binding.emptyState.visibility = android.view.View.GONE
                        binding.errorState.visibility = android.view.View.GONE
                        binding.recyclerView.visibility = android.view.View.GONE
                    }
                    is ListState.Success -> {
                        binding.progressBar.visibility = android.view.View.GONE
                        binding.emptyState.visibility = android.view.View.VISIBLE
                            .let { it ->
                                if (listState.items.isEmpty()) it.visibility = android.view.View.VISIBLE
                                else it.visibility = android.view.View.GONE
                            }
                        binding.errorState.visibility = android.view.View.GONE
                        binding.recyclerView.visibility = android.view.View.VISIBLE
                        adapter?.updateLineList(listState.items)
                    }
                    is ListState.Error -> {
                        binding.progressBar.visibility = android.view.View.GONE
                        binding.emptyState.visibility = android.view.View.GONE
                        binding.errorState.visibility = android.view.View.VISIBLE
                        binding.errorState.message.text = "${listState.exception.message}"
                        binding.recyclerView.visibility = android.view.View.GONE
                        // Add retry listener
                        binding.errorState.retryButton.setOnClickListener {
                            listState = ListState.Loading
                            loadLines()
                        }
                    }
                }
            }
        }
    }

    private fun loadLines() {
        if (listJob != null) listJob.cancel()
        listJob = lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = api.getLines(page = 0, size = 20)
                listState = ListState.Success(response.content)
            } catch (e: Exception) {
                listState = ListState.Error(e)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        listJob?.cancel()
        _binding = null
    }
}