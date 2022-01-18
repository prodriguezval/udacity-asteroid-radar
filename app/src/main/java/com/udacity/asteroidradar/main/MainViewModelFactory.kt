package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.infrastructure.repository.NasaRepository

class MainViewModelFactory(
    private val nasaRepository: NasaRepository,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(nasaRepository, application) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }

}