package com.udacity.asteroidradar.infrastructure.repository

import android.util.Log
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.infrastructure.network.NasaApi
import com.udacity.asteroidradar.infrastructure.network.NasaApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class NasaRepository {
    private val nasaNetwork: NasaApiService = NasaApi.httpService;

    suspend fun getAsteroids(startDate: String, endDate: String): List<Asteroid> {
        var asteroids: List<Asteroid>

        withContext(Dispatchers.IO) {
            val asteroidRawData = JSONObject(nasaNetwork.getAsteroidData(startDate, endDate))
            Log.i("NasaRepository", asteroidRawData.toString());
            asteroids = parseAsteroidsJsonResult(asteroidRawData)
        }

        return asteroids
    }

    suspend fun getPictureOfDay(): PictureOfDay {
        var pictureOfDay: PictureOfDay
        withContext(Dispatchers.IO) {
            pictureOfDay = nasaNetwork.getPictureOfDay()
        }
        return pictureOfDay
    }
}