package com.example.weatherapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.domain.Weather
import com.example.weatherapp.data.source.LocationRepository
import com.example.weatherapp.data.source.WeatherRepository
import kotlinx.coroutines.cancel
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository
) :
    ViewModel() {


    val weather: LiveData<List<Weather>> = Transformations.switchMap(locationRepository.getLastLocation()) {
        weatherRepository.observeWeather(it.latitude, it.longitude, viewModelScope)
    }

    fun refreshLocationData() {
        locationRepository.refreshLocationData()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}