package com.example.weatherapp.di.module

import com.example.weatherapp.data.source.network.OpenWeatherService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(
    includes = [
        NetworkModule::class
    ]
)
class AppModule {

    @Singleton
    @Provides
    fun provideOpenWeatherService(retrofit: Retrofit): OpenWeatherService {
        return retrofit.create(OpenWeatherService::class.java)
    }
}