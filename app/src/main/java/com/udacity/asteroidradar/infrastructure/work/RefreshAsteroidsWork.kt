package com.udacity.asteroidradar.infrastructure.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.infrastructure.database.SpaceDatabase
import com.udacity.asteroidradar.infrastructure.repository.NasaRepository

class RefreshAsteroidsWork(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val asteroidDao = SpaceDatabase.getInstance(applicationContext).asteroidDao
        val repository = NasaRepository(asteroidDao)

        return try {
            repository.saveAsteroids()
            Result.success()
        } catch (exception: Exception) {
            Result.retry()
        }
    }
}