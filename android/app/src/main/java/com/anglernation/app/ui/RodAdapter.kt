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

    inner class RodViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        private val nameText: TextView = view.findViewById(android.R.id.text1)

        init {
            nameText.text = ""
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return RodViewHolder(view)
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