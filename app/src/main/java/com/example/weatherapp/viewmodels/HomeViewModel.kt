package com.example.weatherapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.source.DefaultLocationRepository
import com.example.weatherapp.data.source.DefaultWeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val defaultWeatherRepository: DefaultWeatherRepository,
    private val defaultLocationRepository: DefaultLocationRepository
) :
    ViewModel() {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _buttonClicked = MutableLiveData<Boolean>()

    val buttonClicked: LiveData<Boolean>
        get() = _buttonClicked

    val weather = Transformations.switchMap(defaultLocationRepository.location) {
        defaultWeatherRepository.observeWeather(it.latitude, it.longitude, viewModelScope)
    }

    fun doneClicking() {
        _buttonClicked.value = null
    }

    fun setButtonClicked() {
        _buttonClicked.value = true
    }

    fun refreshCurrentCoordinates() {
        defaultLocationRepository.refreshCurrentCoordinates()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}