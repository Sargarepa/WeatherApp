package com.example.weatherapp.data.domain

import com.example.weatherapp.data.source.database.DatabaseWeather
import java.util.*

data class Weather(
    val temp: Float,
    val date: Date,
    val humidity: Int,
    val wind: Float
)

fun List<Weather>.asDatabaseModelWeatherList(): Array<DatabaseWeather> {
    return map {
        DatabaseWeather(
            id = 0,
            temp = it.temp,
            date = it.date.time,
            humidity = it.humidity,
            wind = it.wind
        )
    }.toTypedArray()
}