package com.udacity.asteroidradar.main

import androidx.recyclerview.widget.DiffUtil
import com.udacity.asteroidradar.Asteroid

class AsteroidListDiffUtil : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid) = (oldItem.id == newItem.id)
    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid) = (oldItem == newItem)
}