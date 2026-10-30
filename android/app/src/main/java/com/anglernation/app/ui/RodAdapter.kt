package com.anglernation.app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anglernation.app.ui.databinding.RodItemBinding

class RodAdapter(
    private val rodList: List<RodDto>,
    onRodClick: (RodDto) -> Unit
) : RecyclerView.Adapter<RodAdapter.RodViewHolder>() {

    inner class RodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameText: TextView = view.findViewById(com.anglernation.app.ui.R.id.name)

        init {
            nameText.text = ""
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RodViewHolder {
        val binding = RodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RodViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RodViewHolder, position: Int) {
        val rod = rodList[position]
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