package com.example.weatherapp


import android.app.Application
import android.content.Context
import androidx.multidex.MultiDexApplication
import com.example.weatherapp.di.component.AppComponent


class WeatherAppApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

}

val Context.application
    get() = applicationContext as WeatherAppApplication