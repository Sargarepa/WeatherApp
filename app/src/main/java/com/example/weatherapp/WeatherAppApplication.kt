package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.di.component.AppComponent
import com.example.weatherapp.di.component.DaggerAppComponent

class WeatherAppApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

}