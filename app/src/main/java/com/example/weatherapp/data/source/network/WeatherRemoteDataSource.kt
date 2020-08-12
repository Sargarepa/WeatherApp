package com.example.weatherapp.data.source.network

import com.example.weatherapp.data.Result
import com.example.weatherapp.data.domain.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(private val OWService: OpenWeatherService) {

    suspend fun getWeather(lat: Double, lon: Double): Result<List<Weather>> {
        return withContext(Dispatchers.IO) {
            val networkWeatherAsync = async { OWService.getWeatherData(lat, lon) }

            val networkWeatherResponse = networkWeatherAsync.await()

            if (networkWeatherResponse.isSuccessful) {
                val networkWeatherBody = networkWeatherResponse.body()
                if (networkWeatherBody != null) {
                    val domainWeather = networkWeatherBody.asDomainModelWeatherList()
                    Result.success(domainWeather)
                } else {
                    Result.error("Results were empty")
                }
            } else {
                Result.error(" ${networkWeatherResponse.code()} ${networkWeatherResponse.message()}")
            }
        }
    }

}