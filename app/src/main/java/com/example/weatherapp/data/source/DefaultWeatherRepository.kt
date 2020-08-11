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
import javax.inject.Inject

class DefaultWeatherRepository @Inject constructor(
    private val weatherDao: WeatherDao,
    private val weatherRemoteDataSource: WeatherRemoteDataSource
) : WeatherRepository {

    override fun observeWeather(
        connectivityAvailable: Boolean,
        coroutineScope: CoroutineScope
    ): LiveData<List<Weather>> {
        if (connectivityAvailable) {
            coroutineScope.launch {
                refreshWeather(20.0f, 44.0f)
            }
        }
        val calendar: Calendar = GregorianCalendar()
        val startTime = getStartTime(calendar)
        val endTime = getEndTime(calendar)

        return Transformations.map(weatherDao.getWeatherForInterval(startTime, endTime)) {
            it.map { it.asDomainModelWeather() }
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

    private fun getStartTime(calendar: Calendar): Long {
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        return calendar.timeInMillis
    }

    private fun getEndTime(calendar: Calendar): Long {
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        return calendar.timeInMillis
    }
}