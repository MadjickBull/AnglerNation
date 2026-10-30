package com.anglernation.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anglernation.app.ui.models.CommentDto
import com.anglernation.app.ui.adapters.CommentAdapter
import com.anglernation.app.ui.ui.CommentFragmentBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

class CommentFragment : Fragment() {

    private var _binding: CommentFragmentBinding? = null
    private val binding get() = _binding!!

    private val api = AnglerNationApi.create()
    private val commentList = MutableStateFlow<List<CommentDto>>(emptyList())
    private var listJob: kotlinx.coroutines.Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CommentFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = CommentAdapter(commentList.asFlow().collectAsList()) { comment ->
            // Handle comment click
        }

        // Load comments
        loadComments()
    }

    private fun loadComments() {
        if (listJob != null) listJob.cancel()
        listJob = lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = api.getComments(page = 0, size = 20)
                commentList.value = response.content
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