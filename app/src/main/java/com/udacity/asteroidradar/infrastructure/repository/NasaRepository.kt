package com.udacity.asteroidradar.infrastructure.repository

import android.util.Log
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.infrastructure.network.nasa.NasaApi
import com.udacity.asteroidradar.infrastructure.network.nasa.NasaApiService
import org.json.JSONObject

class NasaRepository {
    private val nasaNetwork: NasaApiService = NasaApi.httpService;

    suspend fun getAsteroids(startDate: String, endDate: String): List<Asteroid> {
        val asteroidRawData = JSONObject(nasaNetwork.getAsteroidData(startDate, endDate))
        Log.i("NasaRepository", asteroidRawData.toString());
        return parseAsteroidsJsonResult(asteroidRawData)
    }

    suspend fun getImageOfDay(): PictureOfDay = nasaNetwork.getImageOfTheDay()
}