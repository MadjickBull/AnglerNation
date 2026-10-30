package com.anglernation.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anglernation.app.ui.models.FishDto
import com.anglernation.app.ui.databinding.FishItemBinding

class FishAdapter(
    private val fishList: List<FishDto>,
    private val onFishClick: (FishDto) -> Unit
) : RecyclerView.Adapter<FishAdapter.FishViewHolder>() {

    inner class FishViewHolder(val binding: FishItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val nameText: android.widget.TextView = binding.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FishViewHolder {
        val binding = FishItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FishViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FishViewHolder, position: Int) {
        val fish = fishList[position]
        holder.binding.fish = fish
        holder.nameText.text = fish.name
        holder.itemView.setOnClickListener {
            onFishClick(fish)
        }
    }

    override fun getItemCount(): Int = fishList.size

    fun updateFishList(newList: List<FishDto>) {
        fishList.clear()
        fishList.addAll(newList)
        notifyDataSetChanged()
    }
}