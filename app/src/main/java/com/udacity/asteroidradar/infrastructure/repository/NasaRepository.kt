package com.udacity.asteroidradar.infrastructure.repository

import android.util.Log
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.getSeventhDayFromToday
import com.udacity.asteroidradar.getToday
import com.udacity.asteroidradar.infrastructure.database.AsteroidDao
import com.udacity.asteroidradar.infrastructure.database.entity.asAsteroids
import com.udacity.asteroidradar.infrastructure.database.entity.asEntities
import com.udacity.asteroidradar.infrastructure.network.NasaApi
import com.udacity.asteroidradar.infrastructure.network.NasaApiService
import com.udacity.asteroidradar.infrastructure.network.parseAsteroidsJsonResult
import com.udacity.asteroidradar.main.PictureOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

private const val TAG = "NasaRepository"

class NasaRepository(private val asteroidDao: AsteroidDao) {
    private val nasaNetwork: NasaApiService = NasaApi.httpService;

    suspend fun saveAsteroids() {
        withContext(Dispatchers.IO) {
            val startDate = getToday()
            val endDate = getSeventhDayFromToday()
            val asteroidRawData = JSONObject(nasaNetwork.getAsteroidData(startDate, endDate))
            val asteroids = parseAsteroidsJsonResult(asteroidRawData)
            Log.i(TAG, "we got ${asteroids.size} asteroids, from $startDate to $endDate")
            asteroidDao.saveAsteroids(asteroids.asEntities())
        }
    }

    suspend fun getAsteroidsByDate(startDate: String, endDate: String): List<Asteroid> {
        var asteroids: List<Asteroid>
        withContext(Dispatchers.IO) {
            asteroids =
                asteroidDao.getAsteroidsByCloseApproachDate(startDate, endDate).asAsteroids()
        }
        return asteroids
    }


    suspend fun getAsteroids(): List<Asteroid> {
        var asteroids: List<Asteroid>
        withContext(Dispatchers.IO) {
            asteroids = asteroidDao.getAsteroids().asAsteroids()
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