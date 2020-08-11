package com.example.weatherapp


import android.app.Application
import android.content.Context
import com.example.weatherapp.di.component.AppComponent
import com.example.weatherapp.di.component.DaggerAppComponent


class WeatherAppApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

}

val Context.application
    get() = applicationContext as WeatherAppApplication