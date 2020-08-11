package com.example.weatherapp.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.weatherapp.data.domain.Weather
import com.example.weatherapp.data.domain.asDatabaseModelWeatherList
import com.example.weatherapp.data.source.database.WeatherDao
import com.example.weatherapp.data.source.database.asDomainModelWeather
import com.example.weatherapp.data.source.network.WeatherRemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import java.util.Calendar.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultWeatherRepository @Inject constructor(
    private val weatherDao: WeatherDao,
    private val weatherRemoteDataSource: WeatherRemoteDataSource
) : WeatherRepository {

    override fun observeWeather(
        coroutineScope: CoroutineScope
    ): LiveData<List<Weather>> {
        coroutineScope.launch {
            refreshWeather(20.0f, 44.0f)
        }
        val boundsOfDay = calculateBoundsOfCurrentDay()
        val startOfDay = boundsOfDay.first.time
        val endOfDay = boundsOfDay.second.time

        return Transformations.map(
            weatherDao.getWeatherForInterval(
                startOfDay,
                endOfDay
            )
        ) { databaseWeather ->
            databaseWeather.map { it.asDomainModelWeather() }
        }
    }


    override suspend fun refreshWeather(lat: Float, lon: Float) {
        withContext(Dispatchers.IO) {
            val weatherData = weatherRemoteDataSource.getWeather(lat, lon).data
            weatherData?.apply {
                weatherDao.insertWeather(*weatherData.asDatabaseModelWeatherList())
            }
        }
    }

    fun calculateStartOfDay(calendar: Calendar): Date {
        return calendar.apply {
            set(HOUR_OF_DAY, 0)
            set(MINUTE, 0)
            set(SECOND, 0)
            set(MILLISECOND, 0)
        }.time
    }

    fun calculateStartOfNextDay(calendar: Calendar): Date {
        return calculateStartOfDay(calendar.apply { add(DAY_OF_MONTH, 1) })
    }

    fun calculateBoundsOfCurrentDay(): Pair<Date, Date> {
        val calendar = getInstance()
        return Pair(calculateStartOfDay(calendar), calculateStartOfNextDay(calendar))
    }
}