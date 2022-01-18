package com.udacity.asteroidradar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.ListItemAsteroidBinding

class AsteroidViewHolder private constructor(private val binding: ListItemAsteroidBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Asteroid) {
        binding.asteroid = item
        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup): AsteroidViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemAsteroidBinding.inflate(layoutInflater, parent, false)
            return AsteroidViewHolder(binding)
        }
    }

}