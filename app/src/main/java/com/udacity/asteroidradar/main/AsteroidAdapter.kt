package com.udacity.asteroidradar.main

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.udacity.asteroidradar.Asteroid

class AsteroidAdapter(private val asteroidListener: AsteroidListener) :
    ListAdapter<Asteroid, AsteroidViewHolder>(AsteroidListDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, asteroidListener)
    }

}