package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.infrastructure.repository.NasaRepository
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel(
    private val nasaRepository: NasaRepository,
    app: Application
) : AndroidViewModel(app) {

    val asteroids = nasaRepository.getAsteroids()

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _navigateToAsteroidDetailFragment = MutableLiveData<Asteroid?>()
    val navigateToAsteroidDetailFragment
        get() = _navigateToAsteroidDetailFragment

    init {
        viewModelScope.launch {
            try {
                getPictureOfDay()
                refreshAsteroids()
            } catch (e: Exception) {
                Log.i(TAG, "Error loading remote data ${e.message}")
            }
        }

    }

    fun onAsteroidClick(data: Asteroid) {
        _navigateToAsteroidDetailFragment.value = data
    }

    fun onDetailFragmentNavigated() {
        _navigateToAsteroidDetailFragment.value = null
    }

    private suspend fun getPictureOfDay() {
        _pictureOfDay.value = nasaRepository.getPictureOfDay()

    }

    private suspend fun refreshAsteroids() {
        nasaRepository.saveAsteroids()
    }


}