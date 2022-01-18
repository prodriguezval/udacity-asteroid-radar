package com.udacity.asteroidradar.infrastructure.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.infrastructure.database.AsteroidDao
import com.udacity.asteroidradar.infrastructure.database.entity.asAsteroids
import com.udacity.asteroidradar.infrastructure.database.entity.asEntities
import com.udacity.asteroidradar.infrastructure.network.NasaApi
import com.udacity.asteroidradar.infrastructure.network.NasaApiService
import com.udacity.asteroidradar.main.PictureOfDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class NasaRepository(private val asteroidDao: AsteroidDao) {
    private val nasaNetwork: NasaApiService = NasaApi.httpService;

    suspend fun saveAsteroids() {
        withContext(Dispatchers.IO) {
            val asteroidRawData = JSONObject(nasaNetwork.getAsteroidData("", ""))
            Log.i("NasaRepository", asteroidRawData.toString());
            val asteroids = parseAsteroidsJsonResult(asteroidRawData)
            asteroidDao.saveAsteroids(asteroids.asEntities())
        }
    }

    fun getAsteroids(): LiveData<List<Asteroid>> =
        Transformations.map(asteroidDao.getAsteroids()) {
            it.asAsteroids()
        }


    suspend fun getPictureOfDay(): PictureOfDay {
        var pictureOfDay: PictureOfDay
        withContext(Dispatchers.IO) {
            pictureOfDay = nasaNetwork.getPictureOfDay()
        }
        return pictureOfDay
    }
}