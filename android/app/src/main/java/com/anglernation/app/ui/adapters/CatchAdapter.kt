package com.anglernation.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anglernation.app.ui.models.CatchDto
import com.anglernation.app.ui.databinding.CatchItemBinding

class CatchAdapter(
    private val catchList: List<CatchDto>,
    private val onCatchClick: (CatchDto) -> Unit
) : RecyclerView.Adapter<CatchAdapter.CatchViewHolder>() {

    inner class CatchViewHolder(val binding: CatchItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val nameText: android.widget.TextView = binding.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatchViewHolder {
        val binding = CatchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatchViewHolder, position: Int) {
        val catch = catchList[position]
        holder.binding.catch = catch
        holder.nameText.text = "${catch.rodId} - ${catch.fishId}"
        holder.itemView.setOnClickListener {
            onCatchClick(catch)
        }
    }

    override fun getItemCount(): Int = catchList.size

    fun updateCatchList(newList: List<CatchDto>) {
        catchList.clear()
        catchList.addAll(newList)
        notifyDataSetChanged()
    }
}