package com.example.weatherapp.data.source.network

import com.example.weatherapp.data.domain.Weather
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class NetworkWeatherContainer(
    @Json(name = "hourly")
    val hourly: List<NetworkWeather>,
    @Json(name = "lat")
    val lat: Double,
    @Json(name = "lon")
    val lon: Double
)

@JsonClass(generateAdapter = true)
data class NetworkWeather(
    @Json(name = "temp")
    val temp: Float,
    @Json(name = "humidity")
    val humidity: Int,
    @Json(name = "wind_speed")
    val wind: Float,
    @Json(name = "dt")
    val date: Long
)


fun NetworkWeatherContainer.asDomainModelWeatherList(): List<Weather> {
    return this.hourly.map {
        Weather(
            lat = this.lat,
            lon = this.lon,
            temp = it.temp,
            date = Date(it.date*1000),
            humidity = it.humidity,
            wind = it.wind
        )
    }
}