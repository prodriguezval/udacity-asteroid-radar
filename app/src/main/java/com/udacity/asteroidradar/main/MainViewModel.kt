package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.infrastructure.repository.NasaRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val nasaRepository = NasaRepository()

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    init {
        getPictureOfDay()
        getAsteroids()
    }

    private fun getPictureOfDay() {
        viewModelScope.launch {
            _pictureOfDay.value = nasaRepository.getPictureOfDay()
        }
    }

    private fun getAsteroids() {
        viewModelScope.launch {
            _asteroids.value = nasaRepository.getAsteroids("2022-01-17", "2022-01-24")
        }
    }


}