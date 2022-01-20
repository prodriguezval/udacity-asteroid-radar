package com.udacity.asteroidradar.infrastructure.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.infrastructure.database.entity.AsteroidEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidDao {

    @Query("SELECT * FROM AsteroidEntity WHERE closeApproachDate >= :startDate AND closeApproachDate <= :endDate ORDER BY closeApproachDate ASC")
    fun getAsteroidsByCloseApproachDate(
        startDate: String,
        endDate: String
    ): Flow<List<AsteroidEntity>>

    @Query("SELECT * FROM AsteroidEntity ORDER BY closeApproachDate")
    fun getAsteroids(): Flow<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAsteroids(asteroids: List<AsteroidEntity>)
}