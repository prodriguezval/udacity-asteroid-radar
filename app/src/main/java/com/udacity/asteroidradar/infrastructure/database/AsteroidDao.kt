package com.udacity.asteroidradar.infrastructure.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.infrastructure.database.entity.AsteroidEntity

@Dao
interface AsteroidDao {

    @Query("SELECT * FROM AsteroidEntity WHERE closeApproachDate >= :startDate AND closeApproachDate <= :endDate ORDER BY closeApproachDate ASC")
    fun getAsteroidsByCloseApproachDate(startDate: String, endDate: String): List<AsteroidEntity>

    @Query("SELECT * FROM AsteroidEntity ORDER BY closeApproachDate")
    fun getAsteroids(): List<AsteroidEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAsteroids(asteroids: List<AsteroidEntity>)
}