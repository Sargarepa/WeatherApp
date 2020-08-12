package com.example.weatherapp.data.source.network

import com.example.weatherapp.data.domain.Weather
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class NetworkWeatherContainer(
    @Json(name = "hourly")
    val hourly: List<NetworkWeather>
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



fun NetworkWeather.asDomainModelWeather(): Weather {
    return Weather(
        temp = temp,
        date = Date(date),
        humidity = humidity,
        wind = wind
    )
}

fun List<NetworkWeather>.asDomainModelWeatherList(): List<Weather> {
    return map {
        Weather(
            temp = it.temp,
            date = Date(it.date),
            humidity = it.humidity,
            wind = it.wind
        )
    }
}