package com.example.weatherapp.data.source.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapp.data.domain.Weather
import java.util.*

@Entity
data class DatabaseWeather constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "weather_id")
    val id: Int,
    @ColumnInfo(name = "temperature")
    val temp: Float,
    @ColumnInfo(name = "date")
    val date: Long,
    @ColumnInfo(name = "humidity")
    val humidity: Int,
    @ColumnInfo(name = "wind_speed")
    val wind: Float
)

fun DatabaseWeather.asDomainModelWeather(): Weather {
    return Weather(
        temp = this.temp,
        date = Date(this.date),
        humidity = this.humidity,
        wind = this.wind
    )
}