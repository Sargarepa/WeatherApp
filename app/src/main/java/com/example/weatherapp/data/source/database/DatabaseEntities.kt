package com.example.weatherapp.data.source.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.weatherapp.data.domain.Weather
import java.util.*

@Entity (primaryKeys = arrayOf("latitude", "longitude", "date"))
data class DatabaseWeather constructor(
    @ColumnInfo(name = "latitude")
    val lat: Double,
    @ColumnInfo(name = "longitude")
    val lon: Double,
    @ColumnInfo(name = "date")
    val date: Long,
    @ColumnInfo(name = "temperature")
    val temp: Float,
    @ColumnInfo(name = "humidity")
    val humidity: Int,
    @ColumnInfo(name = "wind_speed")
    val wind: Float
)

fun DatabaseWeather.asDomainModelWeather(): Weather {
    return Weather(
        lat = this.lat,
        lon = this.lon,
        temp = this.temp,
        date = Date(this.date),
        humidity = this.humidity,
        wind = this.wind
    )
}
