package com.example.weatherapp.di.module

import com.example.weatherapp.data.source.DefaultLocationRepository
import com.example.weatherapp.data.source.DefaultWeatherRepository
import com.example.weatherapp.data.source.LocationRepository
import com.example.weatherapp.data.source.WeatherRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideLocationRepository(defaultLocationRepository: DefaultLocationRepository): LocationRepository

    @Binds
    abstract fun provideWeatherRepository(defaultWeatherRepository: DefaultWeatherRepository): WeatherRepository

}