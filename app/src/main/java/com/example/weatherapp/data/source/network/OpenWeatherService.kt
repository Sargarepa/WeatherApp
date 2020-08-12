package com.example.weatherapp.data.source.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"
        const val API_KEY = "0689ddcfcf95a9ddddb55ec1e498b129"
    }

    @GET("/data/2.5/onecall")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = API_KEY
    ): Response<NetworkWeatherContainer>
}