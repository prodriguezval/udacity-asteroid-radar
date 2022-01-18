package com.udacity.asteroidradar.infrastructure.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.infrastructure.database.entity.AsteroidEntity

@Database(version = 1, entities = [AsteroidEntity::class])
abstract class SpaceDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao

    companion object {
        @Volatile
        private lateinit var instance: SpaceDatabase

        fun getInstance(context: Context): SpaceDatabase {
            synchronized(SpaceDatabase::class.java) {
                if (!::instance.isInitialized) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SpaceDatabase::class.java,
                        "asteroid.db"
                    )
                        .build()
                }
            }
            return instance
        }
    }
}