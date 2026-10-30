package com.anglernation.app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anglernation.app.ui.models.LocationDto
import com.anglernation.app.ui.databinding.LocationItemBinding

class LocationAdapter(
    private val locationList: List<LocationDto>,
    private val onLocationClick: (LocationDto) -> Unit
) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    inner class LocationViewHolder(val binding: LocationItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val nameText: android.widget.TextView = binding.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding = LocationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = locationList[position]
        holder.binding.location = location
        holder.nameText.text = location.name
        holder.itemView.setOnClickListener {
            onLocationClick(location)
        }
    }

    override fun getItemCount(): Int = locationList.size

    fun updateLocationList(newList: List<LocationDto>) {
        locationList.clear()
        locationList.addAll(newList)
        notifyDataSetChanged()
    }
}