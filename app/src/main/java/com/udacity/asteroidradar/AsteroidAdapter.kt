package com.udacity.asteroidradar

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class AsteroidAdapter : ListAdapter<Asteroid, AsteroidViewHolder>(AsteroidListDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}