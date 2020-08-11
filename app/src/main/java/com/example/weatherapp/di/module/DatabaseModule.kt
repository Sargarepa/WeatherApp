package com.example.weatherapp.di.module

import android.content.Context
import com.example.weatherapp.data.source.database.WeatherDatabase
import com.example.weatherapp.data.source.database.getDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDb(context: Context) = getDatabase(context)



}