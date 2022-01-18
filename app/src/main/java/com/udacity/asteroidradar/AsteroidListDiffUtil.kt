package com.udacity.asteroidradar

import androidx.recyclerview.widget.DiffUtil

class AsteroidListDiffUtil : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid) = (oldItem.id == newItem.id)
    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid) = (oldItem == newItem)
}