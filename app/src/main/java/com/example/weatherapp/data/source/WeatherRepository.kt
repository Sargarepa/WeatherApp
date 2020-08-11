package com.example.weatherapp.data.source

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.domain.Weather
import kotlinx.coroutines.CoroutineScope

interface WeatherRepository {

    fun observeWeather(
        lat: Double,
        lon: Double,
        coroutineScope: CoroutineScope
    ): LiveData<List<Weather>>

    suspend fun refreshWeather(lat: Double, lon: Double)

}