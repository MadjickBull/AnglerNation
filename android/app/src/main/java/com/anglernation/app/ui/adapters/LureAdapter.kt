package com.anglernation.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anglernation.app.ui.models.LureDto
import com.anglernation.app.ui.databinding.LureItemBinding

class LureAdapter(
    private val lureList: List<LureDto>,
    private val onLureClick: (LureDto) -> Unit
) : RecyclerView.Adapter<LureAdapter.LureViewHolder>() {

    inner class LureViewHolder(val binding: LureItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val nameText: android.widget.TextView = binding.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LureViewHolder {
        val binding = LureItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LureViewHolder, position: Int) {
        val lure = lureList[position]
        holder.binding.lure = lure
        holder.nameText.text = lure.name
        holder.itemView.setOnClickListener {
            onLureClick(lure)
        }
    }

    override fun getItemCount(): Int = lureList.size

    fun updateLureList(newList: List<LureDto>) {
        lureList.clear()
        lureList.addAll(newList)
        notifyDataSetChanged()
    }
}