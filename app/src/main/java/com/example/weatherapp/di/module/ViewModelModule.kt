package com.example.weatherapp.di.module

import androidx.lifecycle.ViewModel
import com.example.weatherapp.di.annotation.ViewModelKey
import com.example.weatherapp.viewmodels.HomeViewModel
import dagger.Binds
import dagger.multibindings.IntoMap

abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

}