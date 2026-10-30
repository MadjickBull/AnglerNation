package com.anglernation.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anglernation.app.ui.models.LineDto
import com.anglernation.app.ui.databinding.LineItemBinding

class LineAdapter(
    private val lineList: List<LineDto>,
    private val onLineClick: (LineDto) -> Unit
) : RecyclerView.Adapter<LineAdapter.LineViewHolder>() {

    inner class LineViewHolder(val binding: LineItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val nameText: android.widget.TextView = binding.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineViewHolder {
        val binding = LineItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LineViewHolder, position: Int) {
        val line = lineList[position]
        holder.binding.line = line
        holder.nameText.text = line.name
        holder.itemView.setOnClickListener {
            onLineClick(line)
        }
    }

    override fun getItemCount(): Int = lineList.size

    fun updateLineList(newList: List<LineDto>) {
        lineList.clear()
        lineList.addAll(newList)
        notifyDataSetChanged()
    }
}