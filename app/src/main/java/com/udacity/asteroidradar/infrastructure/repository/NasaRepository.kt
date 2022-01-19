package com.udacity.asteroidradar.infrastructure.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
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
import java.text.SimpleDateFormat
import java.util.*

const val TAG = "NasaRepository"

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

    private fun getToday() = formatDate(Calendar.getInstance().time)

    private fun getSeventhDayFromToday(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 7)
        return formatDate(calendar.time)
    }

    private fun formatDate(date: Date): String {
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(date)
    }
}