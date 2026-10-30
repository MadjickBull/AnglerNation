package com.anglernation.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anglernation.app.ui.models.RodDto
import com.anglernation.app.ui.databinding.RodItemBinding

class RodAdapter(
    private val rodList: List<RodDto>,
    private val onRodClick: (RodDto) -> Unit
) : RecyclerView.Adapter<RodAdapter.RodViewHolder>() {

    inner class RodViewHolder(val binding: RodItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val nameText: TextView = binding.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RodViewHolder {
        val binding = RodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RodViewHolder, position: Int) {
        val rod = rodList[position]
        holder.binding.rod = rod
        holder.nameText.text = rod.name
        holder.itemView.setOnClickListener {
            onRodClick(rod)
        }
    }

    override fun getItemCount(): Int = rodList.size

    fun updateRodList(newList: List<RodDto>) {
        rodList.clear()
        rodList.addAll(newList)
        notifyDataSetChanged()
    }
}