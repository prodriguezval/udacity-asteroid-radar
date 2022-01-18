package com.udacity.asteroidradar.infrastructure.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.infrastructure.database.entity.AsteroidEntity

@Dao
interface AsteroidDao {
    @Query("SELECT * FROM AsteroidEntity ORDER BY closeApproachDate")
    fun getAsteroids(): LiveData<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAsteroids(asteroids: List<AsteroidEntity>)
}