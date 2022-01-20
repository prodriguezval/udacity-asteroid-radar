package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.getSeventhDayFromToday
import com.udacity.asteroidradar.getToday
import com.udacity.asteroidradar.infrastructure.repository.NasaRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel(
    private val nasaRepository: NasaRepository,
    app: Application
) : AndroidViewModel(app) {

    private var _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _navigateToAsteroidDetailFragment = MutableLiveData<Asteroid?>()
    val navigateToAsteroidDetailFragment
        get() = _navigateToAsteroidDetailFragment

    init {
        viewModelScope.launch {
            initializeAsteroids()
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

    private fun initializeAsteroids() {
        getAsteroidsFromRepository()
    }

    fun onClickViewWeekAsteroids() {
        getAsteroidsFromRepository()
    }

    fun onClickTodayAsteroids() {
        getAsteroidsFromRepository(getToday(), getToday())
    }

    fun onClickSavedAsteroids() {
        _asteroids.value = emptyList()
        viewModelScope.launch {
            nasaRepository.getAsteroids().collect {
                _asteroids.value = it
            }
        }
    }

    private fun getAsteroidsFromRepository(
        startDate: String = getToday(),
        endDate: String = getSeventhDayFromToday()
    ) {
        _asteroids.value = emptyList()
        viewModelScope.launch {
            nasaRepository.getAsteroidsByDate(startDate, endDate).collect {
                _asteroids.value = it
            }
            Log.i(TAG, "asteroids size: ${asteroids.value?.size}")
        }
    }

}