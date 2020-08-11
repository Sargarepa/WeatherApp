package com.example.weatherapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.source.DefaultWeatherRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val defaultWeatherRepository: DefaultWeatherRepository) :
    ViewModel() {



}