package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.infrastructure.database.SpaceDatabase
import com.udacity.asteroidradar.infrastructure.repository.NasaRepository
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val spaceDatabase = SpaceDatabase.getInstance(app)
    private val nasaRepository = NasaRepository(spaceDatabase)

    val asteroids = nasaRepository.getAsteroids()

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _navigateToAsteroidDetailFragment = MutableLiveData<Asteroid?>()
    val navigateToAsteroidDetailFragment
        get() = _navigateToAsteroidDetailFragment

    init {
        getPictureOfDay()
        refreshAsteroids()
    }

    fun onAsteroidClick(data: Asteroid) {
        _navigateToAsteroidDetailFragment.value = data
    }

    fun onDetailFragmentNavigated() {
        _navigateToAsteroidDetailFragment.value = null
    }

    private fun getPictureOfDay() {
        viewModelScope.launch {
            _pictureOfDay.value = nasaRepository.getPictureOfDay()
        }
    }

    private fun refreshAsteroids() {
        viewModelScope.launch {
            try {
                nasaRepository.saveAsteroids()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}