package com.example.weatherapp.di.component

import android.content.Context
import com.example.weatherapp.di.module.DatabaseModule
import com.example.weatherapp.di.module.NetworkModule
import com.example.weatherapp.ui.HomeFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DatabaseModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(fragment: HomeFragment)

}