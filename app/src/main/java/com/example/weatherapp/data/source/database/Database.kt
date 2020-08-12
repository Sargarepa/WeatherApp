package com.example.weatherapp.data.source.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = arrayOf(
        DatabaseWeather::class
    ), version = 3, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class WeatherDatabase: RoomDatabase() {
    abstract val weatherDao: WeatherDao
}

private lateinit var INSTANCE: WeatherDatabase

fun getDatabase(context: Context): WeatherDatabase {
    synchronized(WeatherDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context,
                WeatherDatabase::class.java,
                "weather"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}