package com.example.weatherapp.data.source

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.domain.Weather
import kotlinx.coroutines.CoroutineScope

interface WeatherRepository {

    fun observeWeather(connectivityAvailable: Boolean, coroutineScope: CoroutineScope): LiveData<List<Weather>>

    suspend fun refreshWeather(lat: Float = 20.0f, lon: Float = 44.0f)

}