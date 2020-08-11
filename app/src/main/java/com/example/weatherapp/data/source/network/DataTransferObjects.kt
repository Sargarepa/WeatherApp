package com.example.weatherapp.data.source.network

import com.example.weatherapp.data.domain.Weather
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class NetworkWeatherContainer(
    @Json(name = "list")
    val list: List<NetworkWeather>
)

@JsonClass(generateAdapter = true)
data class NetworkWeather(
    @Json(name = "main")
    val main: NetworkMain,
    @Json(name = "wind")
    val wind: NetworkWind,
    @Json(name = "dt")
    val date: Long
)

@JsonClass(generateAdapter = true)
data class NetworkMain(
    @Json(name = "temp")
    val temp: Float,
    @Json(name = "humidity")
    val humidity: Int
)

@JsonClass(generateAdapter = true)
data class NetworkWind(
    @Json(name = "speed")
    val speed: Float
)

fun NetworkWeather.asDomainModelWeather(): Weather {
    return Weather(
        temp = main.temp,
        date = Date(date),
        humidity = main.humidity,
        wind = wind.speed
    )
}