package com.example.weatherapp.data.source.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(vararg weather: DatabaseWeather)

    @Query("select * from databaseweather where latitude = :lat and longitude = :lon and date >= :start and date < :end order by date asc")
    fun getWeatherForInterval(
        start: Long,
        end: Long,
        lat: Int,
        lon: Int
    ): LiveData<List<DatabaseWeather>>

}