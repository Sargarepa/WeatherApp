package com.example.weatherapp.data.source.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"
        const val API_KEY = "e5723c8dad803953aa7474fff503cf8f"
    }

    @GET("/data/2.5/forecast/hourly")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = API_KEY
    ): Response<NetworkWeatherContainer>
}